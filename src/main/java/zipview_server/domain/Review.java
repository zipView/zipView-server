package zipview_server.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.zipview.user.dto.BaseEntity;
import zipview_server.zipview.user.dto.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@Entity
public class Review extends BaseEntity {

    @Id @GeneratedValue
    @Column(name ="review_id")
    private Long id;

  //  private User
    private int price;


}
