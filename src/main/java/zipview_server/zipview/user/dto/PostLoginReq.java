package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor //
public class PostLoginReq {
    private String email;
    private String password;
}
