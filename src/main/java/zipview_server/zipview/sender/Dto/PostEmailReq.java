package zipview_server.zipview.sender.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostEmailReq {
    private String email;
    private String name;
}
