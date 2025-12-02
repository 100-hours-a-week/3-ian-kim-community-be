package ktb3.full.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_account")
@Entity
public class User extends AuditTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "nickname", nullable = false, unique = true, length = 10)
    private String nickname;

    @Column(name = "profile_image_name", unique = true, length = 255)
    private String profileImageName;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    public static User create(String email, String password, String nickname, String profileImageName) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImageName(profileImageName)
                .isDeleted(false)
                .build();
    }

    public static User create(String email, String password, String nickname) {
        return User.create(email, password, nickname, null);
    }

    @Builder
    private User(String email, String password, String nickname, String profileImageName, boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profileImageName = profileImageName;
        this.isDeleted = isDeleted;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateProfileImageName(String profileImageName) {
        this.profileImageName = profileImageName;
    }

    public void delete() {
        this.isDeleted = true;
        this.auditDeletedAt();
    }
}
