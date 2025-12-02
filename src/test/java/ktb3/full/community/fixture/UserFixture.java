package ktb3.full.community.fixture;

import ktb3.full.community.domain.entity.User;
import org.springframework.test.util.ReflectionTestUtils;

public class UserFixture {

    public static User createUser(String email, String password, String nickname, String profileImageName, boolean isDeleted) {
        return User.builder()
                .email(email == null ? "email@example.com" : email)
                .password(password == null ? "Password123!" : password)
                .nickname(nickname == null ? "name" : nickname)
                .profileImageName(profileImageName == null ? "/images/profile.png" : profileImageName)
                .isDeleted(isDeleted)
                .build();
    }

    public static User createUser() {
        return createUser(null, null, null, null, false);
    }

    public static User createWithEmail(String email) {
        return createUser(email, null, null, null, false);
    }

    public static User createWithNickname(String nickname) {
        return createUser(null, null, nickname, null, false);
    }

    public static User createWithPassword(String password) {
        return createUser(null, password, null, null, false);
    }

    public static User createWithNicknameAndProfileImageName(String nickname, String profileImageName) {
        return createUser(null, null, nickname, profileImageName, false);
    }

    public static User createWithId(long id) {
        User user = createUser();
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    public static User createDeletedWithEmail(String email) {
        return createUser(email, null, null, null, true);
    }

    public static User createWithUnique(String email, String nickname, String profileImageName) {
        return createUser(email, null, nickname, profileImageName, false);
    }

    public static User createWithEmailAndPassword(String email, String password) {
        return createUser(email, password, null, null, false);
    }
}
