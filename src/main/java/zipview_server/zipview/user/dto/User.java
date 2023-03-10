package zipview_server.zipview.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    public User(String id, String email,String nickname,String name,String phone, String provider) {
        this.email = email;
        this.nickname = nickname;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.provider = provider;
    }

    @Id
    private String id;
    private String email;
    private String name;
    @Nullable
    private String password;
    private String nickname;
    private String phone;
    private String profileImg;
    private String provider;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String isDeleted;


}
