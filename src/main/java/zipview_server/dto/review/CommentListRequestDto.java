package zipview_server.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListRequestDto {
    //offset
    private Long reviewId;
    private String content;

    private CommentListRequestDto(Long reviewId, String content) {
        this.reviewId = reviewId;
        this.content = content;
    }

    public static CommentListRequestDto of(Long reviewId, String content) {
        return new CommentListRequestDto(reviewId, content);
    }
}
