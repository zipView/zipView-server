package zipview_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.dto.review.ReviewDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
//@Builder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "review")
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="review_id")
    private Long id;

  //  User 연결!!

    @NotNull
    private int price;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private boolean isHarm;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    private int likeNum;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Residence residence;



    public void addReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        if(reviewImage.getReview() != this)
            reviewImage.setReview(this);
    }

    public void setReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        reviewImage.setReview(this);
    }


    public static Review createReivew(int price, String content, String title, int likeNum, RoomType roomType, Residence residence) {
        Review review = new Review();
  //      review.id = id;
        review.price = price;
        review.title = title;
        review.content = content;
        review.likeNum = likeNum;
        review.roomType = roomType;
        review.residence = residence;
        return review;
    }
}



