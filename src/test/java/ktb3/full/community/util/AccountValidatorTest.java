package ktb3.full.community.util;

import ktb3.full.community.domain.entity.User;
import ktb3.full.community.fixture.UserFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AccountValidatorTest {

    @Nested
    class getUserId {

        @Test
        void 활성화된_회원이면_회원ID를_반환한다() {
            // given
            User user = UserFixture.createWithId(1L);

            // when
            Long result = AccountValidator.getUserId(user);

            // then
            assertThat(result).isEqualTo(1L);
        }

        @ParameterizedTest
        @ArgumentsSource(UserTestArguments.class)
        void 탈퇴했거나_존재하지_않는_회원이면_null을_반환한다(User user) {
            // given

            // when
            Long result = AccountValidator.getUserId(user);

            // then
            assertThat(result).isNull();
        }
    }

    @Nested
    class getAuthorName {

        @Test
        void 활성화된_회원이면_닉네임을_반환한다() {
            // given
            User user = UserFixture.createWithNickname("nickname");

            // when
            String result = AccountValidator.getAuthorName(user);

            // then
            assertThat(result).isEqualTo("nickname");
        }

        @ParameterizedTest
        @ArgumentsSource(UserTestArguments.class)
        void 탈퇴했거나_존재하지_않는_회원이면_탈퇴_문구를_반환한다(User user) {
            // given

            // when
            String result = AccountValidator.getAuthorName(user);

            // then
            assertThat(result).isEqualTo("(탈퇴한 회원)");
        }
    }

    @Nested
    class getAuthorProfileImageName {

        @Test
        void 활성화된_회원이면_프로필_이미지_파일명을_반환한다() {
            // given
            User user = UserFixture.createWithProfileImageName("profileImageName");

            // when
            String result = AccountValidator.getAuthorProfileImageName(user);

            // then
            assertThat(result).isEqualTo("profileImageName");
        }

        @ParameterizedTest
        @ArgumentsSource(UserTestArguments.class)
        void 탈퇴했거나_존재하지_않는_회원이면_null을_반환한다(User user) {
            // given

            // when
            String result = AccountValidator.getAuthorProfileImageName(user);

            // then
            assertThat(result).isNull();
        }
    }

    static class UserTestArguments implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.arguments(UserFixture.createDeleted()),
                    Arguments.arguments((Object) null)
            );
        }
    }
}