package zipview_server.zipview.user;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;
import zipview_server.zipview.user.dto.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter @Setter
public class User extends BaseEntity {
    @Id
    private String id;
    @NotNull
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private String profileImg;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String isDeleted;
}
