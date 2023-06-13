package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zipview_server.domain.Comment;
import zipview_server.domain.Review;
import zipview_server.dto.review.CommentListDto;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<CommentListDto> findAllByReview(Review review);

    @Query("select c from Comment c where c.id = :id")
    Comment getComment(@Param("id") Long Id);
}
