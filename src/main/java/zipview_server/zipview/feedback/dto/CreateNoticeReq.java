package zipview_server.zipview.feedback.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateNoticeReq {
    private String title;
    private String content;
}
