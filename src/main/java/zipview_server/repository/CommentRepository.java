package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zipview_server.domain.Comment;
import zipview_server.domain.Review;
import zipview_server.dto.review.CommentListDto;
import zipview_server.dto.review.CommentListResponseDto;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<CommentListDto> findAllByReview(Review review);
}
