package ktb3.full.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "post_like",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_post",
                        columnNames = {"user_id", "post_id"}
                )
        }
)
@Entity
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "is_liked", nullable = false)
    private boolean isLiked;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static PostLike create(User user, Post post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .isLiked(true)
                .build();
    }

    @Builder
    private PostLike(User user, Post post, boolean isLiked) {
        this.user = user;
        this.post = post;
        this.isLiked = isLiked;
    }

    public void toggle() {
        this.isLiked = !this.isLiked;
    }
}
