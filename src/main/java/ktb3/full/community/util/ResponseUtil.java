package ktb3.full.community.util;

import jakarta.servlet.http.HttpServletResponse;
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
}
