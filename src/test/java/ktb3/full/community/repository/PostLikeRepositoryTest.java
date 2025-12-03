package ktb3.full.community.repository;

import ktb3.full.community.RepositoryTestSupport;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.fixture.PostFixture;
import ktb3.full.community.fixture.PostLikeFixture;
import ktb3.full.community.fixture.UserFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PostLikeRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Nested
    class existsAndLiked {

        @Test
        void 회원이_게시글에_좋아요를_한번도_누른적_없으면_null을_반환한다() {
            // given
            long userId = 1L;
            long postId = 1L;

            // when
            Optional<Boolean> result = postLikeRepository.existsAndLiked(userId, postId);

            // then
            assertThat(result).isEmpty();
        }

        @Test
        void 회원이_게시글에_좋아요를_누른_상태면_true를_반환한다() {
            // given
            User user = userRepository.save(UserFixture.createUser());
            Post post = postRepository.save(PostFixture.createPost());
            postLikeRepository.save(PostLikeFixture.createLiked(user, post));

            // when
            Optional<Boolean> result = postLikeRepository.existsAndLiked(user.getId(), post.getId());

             // then
            assertThat(result).hasValue(true);
        }

        @Test
        void 회원이_게시글에_좋아요를_누르지_않은_상태면_false를_반환한다() {
            // given
            User user = userRepository.save(UserFixture.createUser());
            Post post = postRepository.save(PostFixture.createPost());
            postLikeRepository.save(PostLikeFixture.createNotLiked(user, post));

            // when
            Optional<Boolean> result = postLikeRepository.existsAndLiked(user.getId(), post.getId());

            // then
            assertThat(result).hasValue(false);
        }
    }
}