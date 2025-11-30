package ktb3.full.community.security.access;

import ktb3.full.community.common.exception.CommentNotFoundException;
import ktb3.full.community.domain.entity.Comment;
import ktb3.full.community.repository.CommentRepository;
import ktb3.full.community.security.userdetails.AuthUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentPermissionService implements PermissionService {

    private final CommentRepository commentRepository;

    @Override
    public boolean isOwner(Object targetId, Object principal) {
        Long commentId = (Long) targetId;
        Long userId = ((AuthUserDetails) principal).getUserId();
        Comment comment = commentRepository.findByIdActive(commentId).orElseThrow(CommentNotFoundException::new);
        return userId.equals(comment.getUser().getId());
    }
}