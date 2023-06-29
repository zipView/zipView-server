package zipview_server.zipview.feedback.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoticeReq {
    private String title;
    private String content;

}
