package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zipview_server.domain.Review;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.id = :id")
    Optional<Review> findById(@Param("id") Long Id);

    @Query("select r from Review r where r.id = :id")
    Review getReview(@Param("id") Long Id);



}
