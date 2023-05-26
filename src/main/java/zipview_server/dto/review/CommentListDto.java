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



    public static CommentListDto of(Long id, String content) {
        return new CommentListDto(id, content);
    }


}
