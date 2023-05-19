package zipview_server.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "review_image")
@Getter
public class ReviewImage extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String path;
    private int num;
    private boolean isHarm;

}
