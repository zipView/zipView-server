package zipview_server.zipview.sender.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEmailReq {
    private String email;
    private String name;
    private String phone;
}
