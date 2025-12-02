package ktb3.full.community.fixture;

import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.PostLike;
import ktb3.full.community.domain.entity.User;

public class PostLikeFixture {

    public static PostLike create(User user, Post post, boolean isLiked) {
        return PostLike.builder()
                .post(post)
                .user(user)
                .isLiked(isLiked)
                .build();
    }

    public static PostLike createLiked(User user, Post post) {
        return create(user, post, true);
    }

    public static PostLike createNotLiked(User user, Post post) {
        return create(user, post, false);
    }
}
