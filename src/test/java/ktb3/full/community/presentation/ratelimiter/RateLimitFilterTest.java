package ktb3.full.community.presentation.ratelimiter;

import ktb3.full.community.ControllerTestSupport;
import ktb3.full.community.presentation.controller.PostApiController;
import ktb3.full.community.stub.StubTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        PostApiController.class
})
class RateLimitFilterTest extends ControllerTestSupport {

    @MockitoBean
    private TokenBucketRegistry tokenBucketRegistry;

    @Test
    void 버킷의_토큰수를_초과하지_않으면_요청이_허용된다() throws Exception {
        // given
        StubTime time = new StubTime(LocalDateTime.of(2025, 12, 4, 20, 0, 0));
        TokenBucket tokenBucket = new TokenBucket(1, 0, 1, time);

        given(tokenBucketRegistry.resolveBucket(any(String.class))).willReturn(tokenBucket);

        // when
        ResultActions resultActions = mockMvc.perform(get("/posts"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").isEmpty())
                .andExpect(jsonPath("$.message").value("요청에 성공했습니다."))
                .andExpect(jsonPath("$.data").isEmpty());;
    }

    @Test
    void 버킷의_토큰수를_초과해_요청하면_요청이_거부된다() throws Exception {
        // given
        StubTime time = new StubTime(LocalDateTime.of(2025, 12, 4, 20, 0, 0));
        TokenBucket tokenBucket = new TokenBucket(0, 0, 1, time);

        given(tokenBucketRegistry.resolveBucket(any(String.class))).willReturn(tokenBucket);

        // when
        ResultActions resultActions = mockMvc.perform(get("/posts"));

        // then
        resultActions
                .andExpect(status().isTooManyRequests())
                .andExpect(jsonPath("$.code").value(4291))
                .andExpect(jsonPath("$.message").value("요청 한도를 초과했습니다."))
                .andExpect(jsonPath("$.data").isEmpty());;
    }
}