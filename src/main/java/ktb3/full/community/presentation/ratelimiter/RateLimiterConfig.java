package ktb3.full.community.presentation.ratelimiter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import lombok.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties(RateLimiterProperties.class)
@Configuration
public class RateLimiterConfig {

    @Bean
    public Cache<@NonNull Object, Bucket> rateLimitCache(RateLimiterProperties props) {
        return Caffeine.newBuilder()
                .maximumSize(props.getCache().getMaximumSize())
                .expireAfterAccess(props.getCache().getExpireAfterAccess(), TimeUnit.MILLISECONDS)
                .build();
    }
}
