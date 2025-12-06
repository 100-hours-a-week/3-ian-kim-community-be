package ktb3.full.community.presentation.ratelimiter.bucket;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import ktb3.full.community.presentation.ratelimiter.RateLimiterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@RequiredArgsConstructor
@Component
public class BucketFactory {

    private final RateLimiterProperties props;

    public Bucket createBucket() {
        Bandwidth bandwidth = Bandwidth.builder()
                .capacity(props.getBucket().getCapacity())
                .refillIntervally(props.getBucket().getRefillTokens(), Duration.ofMillis(props.getBucket().getRefillPeriods()))
                .build();

        return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }
}
