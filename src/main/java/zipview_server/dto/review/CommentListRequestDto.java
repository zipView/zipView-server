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

    private CommentListRequestDto(Long id,  String content) {
    //    this.reviewId = reviewId;
       this.id = id;
        this.content = content;
    }

    public static CommentListRequestDto of(Long id, String content) {
        return new CommentListRequestDto(id, content);
    }
}
