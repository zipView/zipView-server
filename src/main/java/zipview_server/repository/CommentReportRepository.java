package zipview_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.domain.CommentReport;
import zipview_server.domain.ReviewReport;

import java.util.Optional;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    @Query("select c from CommentReport c where c.comment.id = :id")
    Optional<CommentReport> getCommentReport(@Param("id") Long Id);

    @Modifying
    @Transactional
    @Query("delete from CommentReport c where c.comment.id = :id" )
    void deleteCommentReport(@Param("id") Long Id);
}
