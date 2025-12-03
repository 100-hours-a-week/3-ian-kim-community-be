package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static ktb3.full.community.common.Constants.MESSAGE_NOT_NULL_POST_CONTENT;
import static ktb3.full.community.common.Constants.MESSAGE_NOT_NULL_POST_TITLE;

@Schema(title = "게시글 생성 요청 DTO")
@Getter
@Builder
@RequiredArgsConstructor
public class PostCreateRequest {

    @Schema(description = "제목", example = "테스트 제목입니다.")
    @NotBlank(message = MESSAGE_NOT_NULL_POST_TITLE)
    private final String title;

    @Schema(description = "내용", example = "테스트 게시글입니다.")
    @NotBlank(message = MESSAGE_NOT_NULL_POST_CONTENT)
    private final String content;

    @Schema(description = "이미지 원본 파일명", example = "https://test.kr/test.jpg")
    private final String originImageName;

    @Schema(description = "이미지 파일명", example = "https://test.kr/test.jpg")
    private final String imageName;

    public Post toEntity(User user) {
        return Post.create(user, title, content, originImageName, imageName);
    }
}
