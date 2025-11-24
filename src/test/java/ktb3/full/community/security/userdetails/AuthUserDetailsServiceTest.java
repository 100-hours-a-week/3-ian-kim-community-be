package ktb3.full.community.security.userdetails;

import ktb3.full.community.config.UnitTest;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.fixture.UserFixture;
import ktb3.full.community.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@UnitTest
class AuthUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthUserDetailsService userDetailsService;

    @Nested
    class loadUserByUsername {

        @Test
        void 이메일이_존재하면_조회에_성공한다() {
            // given
            User user = UserFixture.createUser("email@example.com", "Password123!", "name", "profileImageName", false);

            given(userRepository.findByEmail("email@example.com")).willReturn(Optional.of(user));

            // when
            AuthUserDetails userDetails = (AuthUserDetails) userDetailsService.loadUserByUsername("email@example.com");

            // then
            assertThat(userDetails.getUsername()).isEqualTo("email@example.com");
            assertThat(userDetails.getPassword()).isEqualTo("Password123!");
            assertThat(userDetails.getProfileImageName()).isEqualTo("profileImageName");
        }

        @Test
        void 이메일이_존재하지_않으면_예외가_발생한다() {
            // given
            given(userRepository.findByEmail("email@example.com")).willReturn(Optional.empty());

            // when & then
            assertThatThrownBy(() -> userDetailsService.loadUserByUsername("email@example.com"))
                    .isInstanceOf(UsernameNotFoundException.class);
        }

        @Test
        void 이메일이_존재해도_탈퇴한_회원이면_예외가_발생한다() {
            // given
            User user = UserFixture.createDeletedWithEmail("email@example.com");

            given(userRepository.findByEmail("email@example.com")).willReturn(Optional.of(user));

            // when & then
            assertThatThrownBy(() -> userDetailsService.loadUserByUsername("email@example.com"))
                    .isInstanceOf(UsernameNotFoundException.class);
        }
    }
}