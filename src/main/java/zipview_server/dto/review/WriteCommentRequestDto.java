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
    private String content;

    public Comment toComment(Review review, String content) {
        return Comment.of(review, content);
    }


}