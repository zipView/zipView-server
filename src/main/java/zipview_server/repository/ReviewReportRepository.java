package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.domain.Comment;
import zipview_server.domain.ReviewReport;

import java.util.Optional;

public interface ReviewReportRepository extends JpaRepository<ReviewReport, Long> {


    @Query("select r from ReviewReport r where r.review.id = :id")
    Optional<ReviewReport> getReviewReport(@Param("id") Long Id);

    @Modifying
    @Transactional
    @Query("delete from ReviewReport r where r.review.id = :id" )
    void deleteReviewReport(@Param("id") Long Id);
}

