package zipview_server.zipview.user.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateUserReq extends BaseEntity{
    private String email;
    private String nickname;
    private String phone;
    private String password;

}
