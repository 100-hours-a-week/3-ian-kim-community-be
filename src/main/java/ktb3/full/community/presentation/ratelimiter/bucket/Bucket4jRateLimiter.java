package ktb3.full.community.presentation.ratelimiter.bucket;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import ktb3.full.community.presentation.ratelimiter.RateLimitResult;
import ktb3.full.community.presentation.ratelimiter.RateLimitType;
import ktb3.full.community.presentation.ratelimiter.RateLimiter;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Getter
@RequiredArgsConstructor
@Service
public class Bucket4jRateLimiter implements RateLimiter {

    private final Map<RateLimitType, Cache<@NonNull String, Bucket>> cacheMap;
    private final BucketFactory bucketFactory;

    @Override
    public RateLimitResult allowRequest(String clientKey, long numTokensToConsume, RateLimitType rateLimitType) {
        Cache<@NonNull String, Bucket> cache = cacheMap.get(rateLimitType);

        Bucket bucket = Objects.requireNonNull(cache.get(clientKey, key -> bucketFactory.createBucket(rateLimitType)));
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(numTokensToConsume);

        return RateLimitResult.builder()
                .consumed(probe.isConsumed())
                .remaining(probe.getRemainingTokens())
                .refill(TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()))
                .reset(Instant.now().plusNanos(probe.getNanosToWaitForReset()).getEpochSecond())
                .build();
    }
}
