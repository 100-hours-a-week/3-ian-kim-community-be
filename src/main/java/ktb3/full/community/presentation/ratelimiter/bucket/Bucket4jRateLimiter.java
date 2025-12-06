package ktb3.full.community.presentation.ratelimiter.bucket;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.bucket4j.Bucket;
import ktb3.full.community.presentation.ratelimiter.RateLimiter;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
@Service
public class Bucket4jRateLimiter implements RateLimiter {

    private final Cache<@NonNull Object, Bucket> cache;
    private final BucketFactory bucketFactory;

    @Override
    public boolean allowRequest(Object clientKey, long numTokensToConsume) {
        Bucket bucket = Objects.requireNonNull(cache.get(clientKey, key -> bucketFactory.createBucket()));
        return bucket.tryConsume(numTokensToConsume);
    }
}
