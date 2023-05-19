package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zipview_server.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
