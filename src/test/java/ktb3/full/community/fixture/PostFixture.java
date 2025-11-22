package ktb3.full.community.fixture;

import ktb3.full.community.domain.entity.Post;
import ktb3.full.community.domain.entity.User;

import java.util.ArrayList;
import java.util.List;

public class PostFixture {

    public static Post createPost(User user, String title, String content, String originImageName, String imageName) {
        return Post.create(
                user,
                title == null ? "Post Title" : title,
                content == null ? "this is post content" : content,
                originImageName,
                imageName
        );
    }

    public static Post createPost(User user) {
        return createPost(user, null, null, null, null);
    }

    public static List<Post> createPosts(User user, int count) {
        List<Post> posts =  new ArrayList<>();
        for (int i = 0; i < count; i++) {
            posts.add(PostFixture.createPost(user));
        }
        return posts;
    }
}
