package zipview_server.dto.review;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.domain.Comment;
import zipview_server.domain.Review;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WriteCommentRequestDto {

   // private Long reviewId;
    private Long id;
    private String content;
    private int report;

    public Comment toComment(Review review, Long id, String content, int report) {
        return Comment.of(review,id, content, report);
    }


}
