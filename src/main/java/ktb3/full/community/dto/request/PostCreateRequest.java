package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ktb3.full.community.common.annotation.constraint.PostContentPattern;
import ktb3.full.community.common.annotation.constraint.PostTitlePattern;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import static ktb3.full.community.common.Constants.MESSAGE_NOT_NULL_POST_CONTENT;
import static ktb3.full.community.common.Constants.MESSAGE_NOT_NULL_POST_TITLE;

@Schema(title = "게시글 생성 요청 DTO")
@Getter
@Builder
@RequiredArgsConstructor
public class PostCreateRequest {

    @Schema(description = "제목", example = "테스트 제목입니다.")
    @NotNull(message = MESSAGE_NOT_NULL_POST_TITLE)
    @PostTitlePattern
    private final String title;

    @Schema(description = "내용", example = "테스트 게시글입니다.")
    @NotNull(message = MESSAGE_NOT_NULL_POST_CONTENT)
    @PostContentPattern
    private final String content;

    @Schema(description = "이미지", example = "https://test.kr/test.jpg")
    private final MultipartFile image;

    public Post toEntity(User user, String originImageName, String imageName) {
        return Post.create(user, title, content, originImageName, imageName);
    }
}
