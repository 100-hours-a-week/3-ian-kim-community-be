package ktb3.full.community.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ktb3.full.community.common.exception.NoPermissionException;
import ktb3.full.community.dto.response.ApiErrorResponse;
import ktb3.full.community.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("AccessDeniedException: {}", accessDeniedException.getMessage());

        NoPermissionException exception = new NoPermissionException();
        ApiErrorResponse apiResponse = ApiErrorResponse.of(exception.getApiErrorCode(), request.getRequestURI());

        ResponseUtil.responseJsonUtf8(
                response,
                HttpServletResponse.SC_FORBIDDEN,
                objectMapper.writeValueAsString(apiResponse));
    }
}
