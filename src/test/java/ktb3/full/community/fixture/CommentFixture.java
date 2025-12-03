package ktb3.full.community.fixture;

import ktb3.full.community.domain.entity.Comment;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CommentFixture {

    public static Comment createComment(User user, Post post, String content, boolean isDeleted) {
        return Comment.builder()
                .user(user)
                .post(post)
                .content(content == null ? "this is comment content" : content)
                .isDeleted(isDeleted)
                .build();
    }

    public static Comment createComment(User user, Post post, String content) {
        return createComment(user, post, content, false);
    }

    public static Comment createComment() {
        return createComment(null, null, null, false);
    }

    public static Comment createComment(Post post) {
        return createComment(null, post, null, false);
    }

    public static Comment createComment(User user, Post post) {
        return createComment(user, post, null, false);
    }

    public static Comment createDeleted() {
        return createComment(null, null, null, true);
    }

    public static List<Comment> createComments(User user, Post post, int count) {
        List<Comment> comments =  new ArrayList<>();
        for (int i = 0; i < count; i++) {
            comments.add(CommentFixture.createComment(user, post));
        }
        return comments;
    }
}
