package ktb3.full.community.presentation.ratelimiter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import lombok.NonNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties(RateLimiterProperties.class)
@Configuration
public class RateLimiterConfig {

    @Bean
    public Map<RateLimitType, Cache<@NonNull String, Bucket>> rateLimitCache(RateLimiterProperties props) {
        Map<RateLimitType, Cache<@NonNull String, Bucket>> map = new EnumMap<>(RateLimitType.class);

        map.put(RateLimitType.LOGIN, createCache(props.getLogin()));
        map.put(RateLimitType.AUTHENTICATED, createCache(props.getLogin()));
        map.put(RateLimitType.UNAUTHENTICATED, createCache(props.getLogin()));

        return map;
    }

    private Cache<@NonNull String, Bucket> createCache(RateLimiterProperties.PolicyProperties policyProps) {
        RateLimiterProperties.CacheProperties cacheProps = policyProps.getCache();

        return Caffeine.newBuilder()
                .maximumSize(cacheProps.getMaximumSize())
                .expireAfterAccess(cacheProps.getExpireAfterAccess(), TimeUnit.MILLISECONDS)
                .build();
    }
}
