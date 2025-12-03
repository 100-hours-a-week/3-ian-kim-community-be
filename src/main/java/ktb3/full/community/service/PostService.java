package ktb3.full.community.service;

import ktb3.full.community.common.exception.PostNotFoundException;
import ktb3.full.community.common.exception.UserNotFoundException;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.dto.request.PostCreateRequest;
import ktb3.full.community.dto.request.PostUpdateRequest;
import ktb3.full.community.dto.response.PostDetailResponse;
import ktb3.full.community.dto.response.PostResponse;
import ktb3.full.community.repository.PostLikeRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    public PagedModel<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPages = postRepository.findAllActive(pageable);
        return new PagedModel<>(postPages.map(PostResponse::from));
    }

    @Transactional
    public PostDetailResponse getPost(long userId, long postId) {
        postRepository.increaseViewCount(postId);
        Post post = postRepository.findByIdActive(postId).orElseThrow(PostNotFoundException::new);
        boolean isLiked = postLikeRepository.existsAndLiked(userId, postId).orElse(false);
        return PostDetailResponse.from(post, isLiked);
    }

    @Transactional
    public long createPost(long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Post post = request.toEntity(user);
        postRepository.save(post);
        return PostDetailResponse.from(post, false).getPostId();
    }

    @PreAuthorize("@postPermissionService.isOwner(#postId, principal)")
    @Transactional
    public void updatePost(long postId, PostUpdateRequest request) {
        Post post = postRepository.findByIdActive(postId).orElseThrow(PostNotFoundException::new);

        if (request.getTitle() != null) {
            post.updateTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            post.updateContent(request.getContent());
        }

        if (request.getOriginImageName() != null && request.getImageName() != null) {
            post.updateImage(request.getOriginImageName(), request.getImageName());
        }
    }
}
