package zipview_server.dto.req.Comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentListRequestDto {
    //offset
   // private Long reviewId;
    private Long id;
    private String content;
    private int report;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    LocalDateTime createTime;

    private CommentListRequestDto(Long id,  String content,  LocalDateTime createTime, int report) {
    //    this.reviewId = reviewId;
        this.id = id;
        this.createTime = createTime;
        this.content = content;
        this.report = report;
    }

    public static CommentListRequestDto of(Long id, String content, LocalDateTime createTime, int report) {
        return new CommentListRequestDto(id, content, createTime, report);
    }
}
