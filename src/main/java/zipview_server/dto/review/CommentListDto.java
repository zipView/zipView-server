package zipview_server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import zipview_server.domain.Comment;

@Getter
@Data
@AllArgsConstructor
public class CommentListDto {
    private Long id;
    private String content;
    private int report;



    public static CommentListDto of(Long id, String content, int report) {
        return new CommentListDto(id, content, report);
    }


}
