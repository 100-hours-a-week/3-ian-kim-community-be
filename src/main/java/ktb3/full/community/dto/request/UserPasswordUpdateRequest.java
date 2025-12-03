package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ktb3.full.community.common.Constants;
import ktb3.full.community.presentation.validator.NullableNotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "회원 비밀번호 수정 요청 DTO")
@Getter
@RequiredArgsConstructor
public class UserPasswordUpdateRequest {

    @Schema(description = "비밀번호", example = "Testpassword1!")
    @NullableNotBlank(message = Constants.MESSAGE_NULLABLE_NOT_BLANK_PASSWORD)
    private final String password;
}
