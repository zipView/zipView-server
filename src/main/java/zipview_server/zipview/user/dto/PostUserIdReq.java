package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostUserIdReq {
    private String name;
    private String phone;
}
