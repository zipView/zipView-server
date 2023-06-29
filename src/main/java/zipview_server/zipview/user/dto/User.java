package zipview_server.zipview.user.dto;

import jdk.jshell.Snippet;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

<<<<<<< HEAD
<<<<<<< Updated upstream
=======
import javax.persistence.Column;
>>>>>>> 05411ae49f77b0abd7462f9d720d7579b583001f
import javax.persistence.Entity;
import javax.persistence.Id;
=======
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
>>>>>>> Stashed changes

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    public User(String id, String email,String nickname,String name,String phone, String provider) {
        this.email = email;
        this.nickname = nickname;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.provider = provider;
    }

    @Id
    @Column(name ="user_id")
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
    private String role;





}
