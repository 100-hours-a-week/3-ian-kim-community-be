package ktb3.full.community.domain.entity;

import ktb3.full.community.fixture.PostFixture;
import ktb3.full.community.fixture.UserFixture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    @Nested
    class decreaseLikeCount {

        @Test
        void 좋아요수가_양수면_1만큼_감소한다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createWithLikeCount(user, 1);

            // when
            post.decreaseLikeCount();

            // then
            assertThat(post.getLikeCount()).isEqualTo(0);
        }

        @Test
        void 좋아요수가_음수가_되면_예외가_발생한다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createWithLikeCount(user, 0);

            // when & then
            assertThatThrownBy(post::decreaseLikeCount);
            assertThat(post.getLikeCount()).isEqualTo(0);
        }
    }

    @Nested
    class decreaseCommentCount {

        @Test
        void 댓글수가_양수면_1만큼_감소한다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createWithCommentCount(user, 1);

            // when
            post.decreaseCommentCount();

            // then
            assertThat(post.getCommentCount()).isEqualTo(0);
        }

        @Test
        void 댓글수가_음수가_되면_예외가_발생한다() {
            // given
            User user = UserFixture.createUser();
            Post post = PostFixture.createWithCommentCount(user, 0);

            // when & then
            assertThatThrownBy(post::decreaseCommentCount);
            assertThat(post.getCommentCount()).isEqualTo(0);
        }
    }
}