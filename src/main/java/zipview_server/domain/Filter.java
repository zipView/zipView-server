package zipview_server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Getter
public class Filter extends BaseEntity {
    //user 연결!!

    @Id @GeneratedValue
    private Long id;





}
