package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostLoginReq {
    private String email;
    private String password;
}
