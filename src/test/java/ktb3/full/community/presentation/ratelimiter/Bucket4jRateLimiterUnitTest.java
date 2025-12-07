package ktb3.full.community.presentation.ratelimiter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import ktb3.full.community.UnitTestSupport;
import ktb3.full.community.presentation.ratelimiter.bucket.Bucket4jRateLimiter;
import ktb3.full.community.presentation.ratelimiter.bucket.BucketFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class Bucket4jRateLimiterUnitTest extends UnitTestSupport {

    @Mock
    private BucketFactory bucketFactory;

    @Nested
    class allowRequest {

        @Test
        void 키가_캐시에_존재하지_않았으면_버킷을_생성해_캐시에_저장한다() {
            // given
            long cacheMaximumSize = 1;
            RateLimitType type = RateLimitType.LOGIN;

            Bucket bucket = createBucket(1, 60000, 1);
            Map<RateLimitType, Cache<String, Bucket>> cacheMap = createCacheMap(type, cacheMaximumSize, 60000);

            given(bucketFactory.createBucket(type)).willReturn(bucket);

            // when
            Bucket4jRateLimiter sut = new Bucket4jRateLimiter(cacheMap, bucketFactory);
            sut.allowRequest("newKey", 1, type);

            // then
            assertThat(cacheMap.get(type).getIfPresent("newKey")).isNotNull();
        }

        @Test
        void 캐시가_가득차도_새로운_버킷이_저장된다() {
            // given
            long cacheMaximumSize = 1;
            RateLimitType type = RateLimitType.LOGIN;

            Bucket bucket = createBucket(1, 60000, 1);
            Map<RateLimitType, Cache<String, Bucket>> cacheMap = createCacheMap(type, cacheMaximumSize, 60000);
            cacheMap.get(type).put("oldKey", bucket);

            given(bucketFactory.createBucket(type)).willReturn(bucket);

            // when
            Bucket4jRateLimiter sut = new Bucket4jRateLimiter(cacheMap, bucketFactory);
            sut.allowRequest("newKey", 1, type);

            // then
            assertThat(cacheMap.get(type).getIfPresent("newKey")).isNotNull();
        }

        @Test
        void 버킷에_요청수만큼_토큰이_남아있으면_true를_반환한다() {
            // given
            long numTokensToConsume = 1;
            long bucketCapacity = 1;
            RateLimitType type = RateLimitType.LOGIN;

            Bucket bucket = createBucket(bucketCapacity, 60000, 1);
            Map<RateLimitType, Cache<String, Bucket>> cacheMap = createCacheMap(type, 1, 60000);

            given(bucketFactory.createBucket(type)).willReturn(bucket);

            // when
            Bucket4jRateLimiter sut = new Bucket4jRateLimiter(cacheMap, bucketFactory);
            RateLimitResult result = sut.allowRequest("newKey", numTokensToConsume, type);

            // then
            assertThat(result.isConsumed()).isTrue();
        }

        @Test
        void 버킷에_요청수만큼_토큰이_남아있지_않으면_false를_반환한다() {
            // given
            long numTokensToConsume = 2;
            long bucketCapacity = 1;
            RateLimitType type = RateLimitType.LOGIN;

            Bucket bucket = createBucket(bucketCapacity, 60000, 1);
            Map<RateLimitType, Cache<String, Bucket>> cacheMap = createCacheMap(type, 1, 60000);

            given(bucketFactory.createBucket(type)).willReturn(bucket);

            // when
            Bucket4jRateLimiter sut = new Bucket4jRateLimiter(cacheMap, bucketFactory);
            RateLimitResult result = sut.allowRequest("newKey", numTokensToConsume, type);

            // then
            assertThat(result.isConsumed()).isFalse();
        }
    }
    
    private Bucket createBucket(long capacity, long refillTokens, long refillPeriods) {
        Bandwidth bandwidth = Bandwidth.builder()
                .capacity(capacity)
                .refillIntervally(refillTokens, Duration.ofMillis(refillPeriods))
                .build();

        return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    private Map<RateLimitType, Cache<String, Bucket>> createCacheMap(RateLimitType type, long maximumSize, long expireAfterAccess) {
        Map<RateLimitType, Cache<String, Bucket>> map = new EnumMap<>(RateLimitType.class);
        map.put(type, createCache(maximumSize, expireAfterAccess));
        return map;
    }

    private Cache<String, Bucket> createCache(long maximumSize, long expireAfterAccess) {
        return Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfterAccess, TimeUnit.MILLISECONDS)
                .build();
    }
}