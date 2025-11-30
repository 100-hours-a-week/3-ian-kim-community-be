package ktb3.full.community.security.access;

import ktb3.full.community.common.exception.PostNotFoundException;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.security.userdetails.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostPermissionService implements PermissionService {

    private final PostRepository postRepository;

    @Override
    public boolean isOwner(Object targetId, Object principal) {
        Long postId = (Long) targetId;
        Long userId = ((AuthUserDetails) principal).getUserId();
        Post post = postRepository.findByIdActive(postId).orElseThrow(PostNotFoundException::new);
        return userId.equals(post.getUser().getId());
    }
}