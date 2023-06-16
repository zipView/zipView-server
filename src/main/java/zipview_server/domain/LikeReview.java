package zipview_server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReview extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private boolean status;

    //user 연결

    public static LikeReview createLikeReview(Review review) {
        LikeReview likeReview = new LikeReview();
        likeReview.review = review;
        likeReview.status = true;
        //user 연결
        return likeReview;
    }

    public void unLikeReview (Review review) {
        this.status = false;
        review.setHeart(review.getHeart()-1);

    }


}
