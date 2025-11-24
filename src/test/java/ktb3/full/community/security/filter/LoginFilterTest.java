package ktb3.full.community.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ktb3.full.community.common.Constants;
import ktb3.full.community.config.IntegrationTest;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.UserLoginRequest;
import ktb3.full.community.fixture.UserFixture;
import ktb3.full.community.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class LoginFilterTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Nested
    class login {

        String TARGET_URI = Constants.LOGIN;

        @Test
        void 이메일과_비밀번호가_일치하면_로그인에_성공한다() throws Exception {
            // given
            User user = UserFixture.createWithEmailAndPassword("email@example.com", passwordEncoder.encode("Password123!"));
            userRepository.save(user);

            // when
            UserLoginRequest request = new UserLoginRequest("email@example.com", "Password123!");

            ResultActions resultActions = mockMvc.perform(post(TARGET_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.userId").value(user.getId()))
                    .andExpect(jsonPath("$.data.profileImageName").value(user.getProfileImageName()));
        }

        @Test
        void 이메일이_틀리면_로그인에_실패한다() throws Exception {
            // given
            userRepository.save(UserFixture.createWithEmailAndPassword("email@example.com", passwordEncoder.encode("Password123!")));

            // when
            UserLoginRequest request = new UserLoginRequest("wrongEmail@example.com", "Password123!");

            ResultActions resultActions = mockMvc.perform(post(TARGET_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // then
            resultActions
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(4011));
        }

        @Test
        void 비밀번호가_틀리면_로그인에_실패한다() throws Exception {
            // given
            userRepository.save(UserFixture.createWithEmailAndPassword("email@example.com", passwordEncoder.encode("Password123!")));

            // when
            UserLoginRequest request = new UserLoginRequest("email@example.com", "WrongPassword123!");

            ResultActions resultActions = mockMvc.perform(post(TARGET_URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

            // then
            resultActions
                    .andExpect(status().isUnauthorized())
                    .andExpect(jsonPath("$.code").value(4011));
        }
    }
}
