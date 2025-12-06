package ktb3.full.community.presentation.ratelimiter;

import io.github.bucket4j.ConsumptionProbe;

public interface RateLimiter {

    ConsumptionProbe allowRequest(Object clientKey, long numTokensToConsume);
}
