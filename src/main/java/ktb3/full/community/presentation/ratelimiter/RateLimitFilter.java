package ktb3.full.community.presentation.ratelimiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ktb3.full.community.common.exception.ApiErrorCode;
import ktb3.full.community.dto.response.ApiResponse;
import ktb3.full.community.security.userdetails.AuthUserDetails;
import ktb3.full.community.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiter rateLimiter;
    private final ObjectMapper objectMapper;
    private final RateLimiterProperties props;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthUser = false;
        Object target = request.getRemoteAddr();

        if (authentication != null) {
            isAuthUser = true;
            AuthUserDetails principal = (AuthUserDetails) authentication.getPrincipal();
            target = principal.getUserId();
        }

        ConsumptionProbe probe = rateLimiter.allowRequest(target, props.getNumTokensToConsume());
        setRateLimitHeaders(response, probe);

        if (!probe.isConsumed()) {
            setRejectedResponse(response, probe);

            if (isAuthUser) {
                logUserExceeded(target);
                return;
            }

            logGuestExceeded(target);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setRejectedResponse(HttpServletResponse response, ConsumptionProbe probe) throws IOException {
        ApiResponse<Void> apiResponse = ApiResponse.error(ApiErrorCode.TOO_MANY_REQUESTS);
        response.setHeader(HttpHeaders.RETRY_AFTER, String.valueOf(probe.getNanosToWaitForRefill()));
        ResponseUtil.responseJsonUtf8(response, HttpStatus.TOO_MANY_REQUESTS.value(), objectMapper.writeValueAsString(apiResponse));
    }

    private void setRateLimitHeaders(HttpServletResponse response, ConsumptionProbe probe) {
        response.setHeader("X-RateLimit-Limit", String.valueOf(props.getBucket().getCapacity()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(probe.getRemainingTokens()));
        response.setHeader("X-RateLimit-Reset", String.valueOf(probe.getNanosToWaitForReset()));
    }

    private void logUserExceeded(Object target) {
        log.info("Rate limit exceeded for userId = {}", target);
    }

    private void logGuestExceeded(Object target) {
        log.info("Rate limit exceeded for IP Addr: {}", target);
    }
}
