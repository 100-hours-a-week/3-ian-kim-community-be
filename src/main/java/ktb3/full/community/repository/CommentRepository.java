package ktb3.full.community.repository;

import ktb3.full.community.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @NonNull
    @Query(value = "select c from Comment c where c.id = :id and c.isDeleted = false")
    Optional<Comment> findByIdActive(@NonNull @Param("id") Long id);

    @Query(value = "select c from Comment c left join fetch c.user where c.post.id = :postId and c.isDeleted = false order by c.createdAt desc",
            countQuery = "select count(c) from Comment c where c.post.id = :postId and c.isDeleted = false")
    Page<Comment> findAllLatestByPostId(@Param("postId") long postId, Pageable pageable);

    @Modifying
    @Query(value = "update Comment c set c.isDeleted = true, c.deletedAt = CURRENT_TIMESTAMP where c.post.id = :postId")
    void deleteAllByPostId(@Param("postId") long postId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update Comment c set c.user = null where c.user.id = :userId")
    void deleteAllByUserId(@Param("userId") long userId);
}
