package zipview_server.zipview.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateFeedBackReq {
    private String userId;
    private String type;
    private String title;
    private String content;
    private String email;
}
