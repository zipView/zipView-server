package zipview_server.zipview.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String phone;
    private String profileImg;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String isDeleted;
}
