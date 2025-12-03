package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ktb3.full.community.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.community.common.Constants.*;

@Schema(title = "회원 등록 요청 DTO")
@Getter
@Builder
@RequiredArgsConstructor
public class UserRegisterRequest {

    @Schema(description = "이메일", example = "test@test.com")
    @NotBlank(message = MESSAGE_NOT_NULL_EMAIL)
    private final String email;

    @Schema(description = "비밀번호", example = "Testpassword1!")
    @NotBlank(message = MESSAGE_NOT_NULL_PASSWORD)
    private final String password;

    @Schema(description = "닉네임", example = "testNick")
    @NotBlank(message = MESSAGE_NOT_NULL_NICKNAME)
    private final String nickname;

    @Schema(description = "프로필 이미지 파일명", example = "https://test.kr/test.jpg")
    private final String profileImageName;

    public User toUserEntity(String encodedPassword) {
        return User.create(email, encodedPassword, nickname, profileImageName);
    }
}
