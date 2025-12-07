package ktb3.full.community.presentation.ratelimiter;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RateLimitResult {

    private final boolean consumed;
    private final long limit;
    private final long remaining;
    private final long refill;
    private final long reset;
}
