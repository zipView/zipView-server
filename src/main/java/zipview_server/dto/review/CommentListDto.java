package zipview_server.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import zipview_server.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Data
@AllArgsConstructor
public class CommentListDto {
    private Long id;
    private String content;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    LocalDateTime createTime;
    private int report;




    public static CommentListDto of(Long id, String content, LocalDateTime createTime, int report) {
        return new CommentListDto(id, content, createTime, report);
    }


}
