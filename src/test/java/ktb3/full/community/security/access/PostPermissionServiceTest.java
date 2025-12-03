package ktb3.full.community.security.access;

import ktb3.full.community.IntegrationTestSupport;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;
import ktb3.full.community.fixture.PostFixture;
import ktb3.full.community.fixture.UserFixture;
import ktb3.full.community.repository.PostRepository;
import ktb3.full.community.repository.UserRepository;
import ktb3.full.community.security.userdetails.AuthUserDetails;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PostPermissionServiceTest extends IntegrationTestSupport {

    @Autowired
    private PostPermissionService sut;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Nested
    class isOwner {

        @Test
        void 게시글_작성자면_true를_반환한다() {
            // given
            User author = userRepository.save(UserFixture.createUser());
            Post post = postRepository.save(PostFixture.createPost(author));

            AuthUserDetails principal = AuthUserDetails.builder()
                    .userId(author.getId())
                    .build();

            // when
            boolean result = sut.isOwner(post.getId(), principal);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 게시글_작성자가_아니면_false를_반환한다() {
            // given
            User author = userRepository.save(UserFixture.createUser("author@example.com", "author"));
            User user = userRepository.save(UserFixture.createUser("user@example.com", "user"));
            Post post = postRepository.save(PostFixture.createPost(author));

            AuthUserDetails principal = AuthUserDetails.builder()
                    .userId(user.getId())
                    .build();

            // when
            boolean result = sut.isOwner(post.getId(), principal);

            // then
            assertThat(result).isFalse();
        }
    }
}