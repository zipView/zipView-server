package zipview_server.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListRequestDto {
    //offset
   // private Long reviewId;
    private Long id;
    private String content;
    private int report;

    private CommentListRequestDto(Long id,  String content, int report) {
    //    this.reviewId = reviewId;
       this.id = id;
        this.content = content;
        this.report = report;
    }

    public static CommentListRequestDto of(Long id, String content, int report) {
        return new CommentListRequestDto(id, content, report);
    }
}
