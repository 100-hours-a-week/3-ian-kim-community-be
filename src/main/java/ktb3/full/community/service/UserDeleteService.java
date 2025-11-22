package ktb3.full.community.service;

import ktb3.full.community.common.exception.UserNotFoundException;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.repository.CommentRepository;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDeleteService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void deleteAccount(long userId) {
        // soft delete
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        user.delete();
        postRepository.deleteAllByUserId(userId);
        commentRepository.deleteAllByUserId(userId);
    }
}
