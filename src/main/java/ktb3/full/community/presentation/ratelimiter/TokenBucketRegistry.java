package ktb3.full.community.presentation.ratelimiter;

import ktb3.full.community.common.time.BaseTime;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketRegistry {

    private final Map<String, TokenBucket> buckets = new ConcurrentHashMap<>();

    public TokenBucket resolveBucket(String key) {
        return buckets.computeIfAbsent(key, k -> TokenBucket.create(100, 1, 600, new BaseTime()));
    }
}
