package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zipview_server.domain.LikeReview;
import zipview_server.domain.Review;

public interface LikeReviewRepository extends JpaRepository<LikeReview, Long> {

    LikeReview findByReview(Review review); //user 연결
}
