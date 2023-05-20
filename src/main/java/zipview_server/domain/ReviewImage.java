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

    //연관관계 메서드
    public void setReviewImage(Review review) {
        this.review = review;
        review.getReviewImage().add(this);
    }

    public static ReviewImage createReviewImage(Review review, String path, int num, boolean isHarm) {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.path = path;
        reviewImage.num = num;
        reviewImage.isHarm = isHarm;
        this.setReviewImage(review);
        return reviewImage;

    }

}
