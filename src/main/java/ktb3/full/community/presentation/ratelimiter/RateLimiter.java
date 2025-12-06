package ktb3.full.community.presentation.ratelimiter;

public interface RateLimiter {

    boolean allowRequest(String clientKey, long numTokensToConsume);
}
