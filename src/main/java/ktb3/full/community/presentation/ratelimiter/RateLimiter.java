package ktb3.full.community.presentation.ratelimiter;

public interface RateLimiter {

    RateLimitResult allowRequest(String clientKey, long numTokensToConsume, RateLimitType rateLimitType);
}
