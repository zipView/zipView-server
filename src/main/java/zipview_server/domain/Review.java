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

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;*/

    @NotNull
    private Integer price;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewReport> reviewReportList = new ArrayList<>();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<LikeReview> reviewLikeList = new ArrayList<>();

    @NotNull
    private Integer likeNum;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Residence residence;

    @NotNull
    private Integer report;





    public void addReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        if(reviewImage.getReview() != this)
            reviewImage.setReview(this);
    }

    public void setReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        reviewImage.setReview(this);
    }

    public void setLikeNum(int like) {
        this.likeNum = like;
    }

    public void increaseReport() {
        this.report +=1 ;
    }

    public static Review createReivew(int price, String content, String title, int likeNum, RoomType roomType, Residence residence, int report) {
        Review review = new Review();
  //      review.id = id;
    //    review.user = user;
        review.price = price;
        review.title = title;
        review.content = content;
        review.likeNum = likeNum;
        review.roomType = roomType;
        review.residence = residence;
        review.report = report;
        return review;
    }

    public void fixReview(int price, String content, String title, RoomType roomType, Residence residence) {

        this.price = price;
        this.title = title;
        this.content = content;
        this.roomType = roomType;
        this.residence = residence;

    }


}



