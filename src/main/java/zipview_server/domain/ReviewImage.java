package zipview_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "review_image")
@Getter
@NoArgsConstructor
public class ReviewImage extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "review_image_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String name;
    private Long fileSize;
    private String path;
    private boolean isHarm;

    //연관관계 메서드
    public void setReview(Review review) {

        if(this.review != null) {
            System.out.println("리뷰가 있으면 다 삭제해");
            this.review.getReviewImages().remove(this);
        }
        this.review = review;
        if(!review.getReviewImages().contains(this)) {
            System.out.println("리뷰 같은 이미지 들어가있지 않으면 ");
            review.getReviewImages().add(this);
        }
    }

    @Builder
    public ReviewImage(String name, Long fileSize, String path, boolean isHarm) {
        this.name = name;
        this.fileSize = fileSize;
        this.path = path;
        this.isHarm = isHarm;
    }



    public static ReviewImage createReviewImage(Review review, boolean isHarm, String path) {
        ReviewImage reviewImage = new ReviewImage();
        reviewImage.path = path;
   //    reviewImage.num = num;
        reviewImage.isHarm = isHarm;
       reviewImage.setReview(review);
        return reviewImage;

    }

}
