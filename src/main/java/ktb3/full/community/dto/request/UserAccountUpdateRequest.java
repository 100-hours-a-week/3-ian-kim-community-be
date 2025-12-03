package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ktb3.full.community.common.Constants;
import ktb3.full.community.presentation.validator.NullableNotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Schema(title = "회원 정보 수정 요청 DTO")
@Getter
@Builder
@RequiredArgsConstructor
public class UserAccountUpdateRequest {

    @Schema(description = "닉네임", example = "testNick")
    @NullableNotBlank(message = Constants.MESSAGE_NULLABLE_NOT_BLANK_NICKNAME)
    private final String nickname;

    @Schema(description = "프로필 이미지 파일명", example = "https://test.kr/test.jpg")
    private final String profileImageName;
}
