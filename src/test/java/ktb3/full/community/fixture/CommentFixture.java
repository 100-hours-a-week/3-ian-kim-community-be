package ktb3.full.community.fixture;

import ktb3.full.community.domain.entity.Comment;
import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

public class CommentFixture {

    public static Comment createComment(User user, Post post, String content) {
        return Comment.create(
                user,
                post,
                content == null ? "this is comment content" : content
        );
    }

    public static Comment createComment(User user, Post post) {
        return createComment(user, post, null);
    }

    public static List<Comment> createComments(User user, Post post, int count) {
        List<Comment> comments =  new ArrayList<>();
        for (int i = 0; i < count; i++) {
            comments.add(CommentFixture.createComment(user, post));
        }
        return comments;
    }
}
