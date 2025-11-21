package ktb3.full.community.presentation.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import ktb3.full.community.common.annotation.resolver.Authentication;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.ApiSuccessResponse;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostLikeRespnose;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.presentation.api.PostApi;
import ktb3.full.community.service.PostDeleteService;
import ktb3.full.community.service.PostLikeCreateOrUpdateService;
import ktb3.full.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Validated
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostApiController implements PostApi {

    private final PostService postService;
    private final PostDeleteService postDeleteService;
    private final PostLikeCreateOrUpdateService postLikeCreateOrUpdateService;

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<PagedModel<PostResponse>>> getAllPosts(@Valid Pageable pageable) {
        PagedModel<PostResponse> response = postService.getAllPosts(pageable);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<PostDetailResponse>> getPostDetail(
            @Authentication Long loggedInUserId,
            @Positive @PathVariable("postId") long postId) {
        PostDetailResponse response = postService.getPost(loggedInUserId, postId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<PostDetailResponse>> createPost(
            @Authentication Long loggedInUserId,
            @Valid @ModelAttribute PostCreateRequest request
    ) {
        PostDetailResponse response = postService.createPost(loggedInUserId, request);
        return ResponseEntity.created(URI.create(String.format("/posts/%d", response.getPostId())))
                .body(ApiSuccessResponse.of(response));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<PostDetailResponse>> updatePost(
            @Authentication Long loggedInUserId,
            @Positive @PathVariable("postId") long postId,
            @Valid @ModelAttribute PostUpdateRequest request) {
        PostDetailResponse response = postService.updatePost(loggedInUserId, postId, request);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiSuccessResponse<Void>> deletePost(
            @Authentication Long loggedInUserId,
            @Positive @PathVariable("postId") long postId) {
        postDeleteService.deletePost(loggedInUserId, postId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.getBaseResponse());
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<ApiSuccessResponse<PostLikeRespnose>> likePost(
            @Authentication Long loggedInUserId,
            @Positive @PathVariable("postId") long postId) {
        PostLikeRespnose postLikeRespnose = postLikeCreateOrUpdateService.createOrUpdate(loggedInUserId, postId);
        return ResponseEntity.ok()
                .body(ApiSuccessResponse.of(postLikeRespnose));
    }
}
