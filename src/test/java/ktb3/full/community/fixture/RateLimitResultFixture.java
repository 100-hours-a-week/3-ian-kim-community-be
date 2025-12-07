package ktb3.full.community.fixture;

import ktb3.full.community.presentation.ratelimiter.RateLimitResult;

public class RateLimitResultFixture {

    public static RateLimitResult createDisallowedResult() {
        return RateLimitResult.builder()
                .consumed(false)
                .build();
    }

    public static RateLimitResult createAllowedResult() {
        return RateLimitResult.builder()
                .consumed(true)
                .build();
    }
}
