package ktb3.full.community.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import ktb3.full.community.common.exception.ApiErrorCode;
import ktb3.full.community.dto.response.ApiResponse;
import ktb3.full.community.presentation.ratelimiter.RateLimitResult;
import ktb3.full.community.presentation.ratelimiter.RateLimiterProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseUtil {

    public static void responseJsonUtf8(HttpServletResponse response, int status, String data) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(status);
        response.getWriter().write(data);
    }

    public static void responseRateLimitRejected(HttpServletResponse response, RateLimitResult result, ObjectMapper objectMapper) throws IOException {
        ApiResponse<Void> apiResponse = ApiResponse.error(ApiErrorCode.TOO_MANY_REQUESTS);
        response.setHeader(HttpHeaders.RETRY_AFTER, String.valueOf(result.getRefill()));
        ResponseUtil.responseJsonUtf8(response, HttpStatus.TOO_MANY_REQUESTS.value(), objectMapper.writeValueAsString(apiResponse));
    }

    public static void responseRateLimitHeaders(HttpServletResponse response, RateLimitResult result, RateLimiterProperties.PolicyProperties props) {
        response.setHeader("X-RateLimit-Limit", String.valueOf(props.getBucket().getCapacity()));
        response.setHeader("X-RateLimit-Remaining", String.valueOf(result.getRemaining()));
        response.setHeader("X-RateLimit-Reset", String.valueOf(result.getReset()));
    }
}
