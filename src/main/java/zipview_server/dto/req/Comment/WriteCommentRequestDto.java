package zipview_server.dto.req.Comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.domain.Comment;
import zipview_server.domain.Review;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteCommentRequestDto {

   // private Long reviewId;
    private Long id;
    private String content;
    private LocalDateTime createTime;
    private int report;

    public Comment toComment(Review review, Long id, String content, LocalDateTime createTime, int report) {
        return Comment.of(review,id, content, createTime, report);
    }


}
