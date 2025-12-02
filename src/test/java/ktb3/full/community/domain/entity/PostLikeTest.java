package ktb3.full.community.domain.entity;

import ktb3.full.community.fixture.PostFixture;
import ktb3.full.community.fixture.PostLikeFixture;
import ktb3.full.community.fixture.UserFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostLikeTest {

    @Nested
    class create {

        @Test
        void 좋아요_생성_시_좋아요_상태는_true다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createPost(user);

            // when
            PostLike postLike = PostLike.create(user, post);

            // then
            assertThat(postLike.isLiked()).isTrue();
        }
    }

    @Nested
    class toggle {

        @Test
        void 좋아요_상태가_true였으면_false가_된다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createPost(user);
            PostLike postLike = PostLikeFixture.createLiked(user, post);

            // when
            postLike.toggle();

            // then
            assertThat(postLike.isLiked()).isFalse();
        }

        @Test
        void 좋아요_상태가_false였으면_true가_된다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createPost(user);
            PostLike postLike = PostLikeFixture.createNotLiked(user, post);

            // when
            postLike.toggle();

            // then
            assertThat(postLike.isLiked()).isTrue();
        }
    }
}