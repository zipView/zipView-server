package zipview_server.zipview.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import zipview_server.zipview.security.dto.Authority;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {

    @Id
    private String id;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    private String provider;

    public Member(String id, String email, String nickname, String name, String phone, String provider, Authority authority) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.provider = provider;
        this.authority = authority;
    }

    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String isDeleted;
    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String updatedAt;
    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void setId(String id) {this.id = id;}
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setIsDeleted(String deleted) {this.isDeleted=deleted;}
    public void setPassword(String password) { this.password = password; }
    public void setKeyword(String keyword1,String keyword2, String keyword3) {
        this.keyword1=keyword1;
        this.keyword2=keyword2;
        this.keyword3=keyword3;
    }

    @Builder
    public Member(String id, String email, String password, String nickname, String name, String phone, String provider, String keyword1, String keyword2, String keyword3, String isDeleted, String createdAt, String updatedAt, Authority authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.phone = phone;
        this.provider = provider;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.authority = authority;
    }
}
