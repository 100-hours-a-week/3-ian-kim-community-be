package ktb3.full.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ktb3.full.community.common.annotation.constraint.PostContentPattern;
import ktb3.full.community.common.annotation.constraint.PostTitlePattern;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Schema(title = "게시글 수정 요청 DTO")
@Getter
@Builder
@RequiredArgsConstructor
public class PostUpdateRequest {

    @Schema(description = "제목", example = "테스트 제목입니다.")
    @PostTitlePattern
    private final String title;

    @Schema(description = "내용", example = "테스트 게시글입니다.")
    @PostContentPattern
    private final String content;

    @Schema(description = "이미지", example = "https://test.kr/test.jpg")
    private final MultipartFile image;
}
