package zipview_server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.dto.req.Review.WriteReviewRequestDto;
import zipview_server.dto.review.ReviewDto;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
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

    // 숫자 범위 refec
    private int rentMin;
    private int rentMax;

    private int depositMin;
    private int depositMax;

    private int maintenanceFeeMin;
    private int maintenanceFeeMax;

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

    private int heart;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Residence residence;

    private int report;

    @Enumerated(EnumType.STRING)
    private Floor floor;

    @Enumerated(EnumType.STRING)
    private RoomSize roomSize;

    @Enumerated(EnumType.STRING)
    private RoomStructure roomStructure;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;



    public void addReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        if(reviewImage.getReview() != this)
            reviewImage.setReview(this);
    }

    //이미지 수정 Refec
    public void setReviewImage(ReviewImage reviewImage) {
        reviewImages.add(reviewImage);
        reviewImage.setReview(this);
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void increaseReport() {
        this.report +=1 ;
    }

 /*   public static Review createReivew( int rentMin, int rentMax,  int depositMin, int depositMax, int maintenanceFeeMin, int maintenanceFeeMax, String content, String title, int heart, RoomType roomType, Residence residence, int report, Floor floor, RoomSize roomSize, RoomStructure roomStructure, TransactionType transactionType) {
        Review review = new Review();
  //      review.id = id;
      //  review.user = user;
        review.rentMin = rentMin;
        review.rentMax = rentMax;
        review.depositMin = depositMin;
        review.depositMax = depositMax;
        review.maintenanceFeeMin = maintenanceFeeMin;
        review.maintenanceFeeMax = maintenanceFeeMax;
        review.title = title;
        review.content = content;
        review.heart = heart;
        review.roomType = roomType;
        review.residence = residence;
        review.report = report;
        review.floor = floor;
        review.roomSize = roomSize;
        review.roomStructure = roomStructure;
        review.transactionType = transactionType;
        return review;
    }
*/
    public void fixReview(WriteReviewRequestDto writeReviewRequestDto) {

        this.rentMin = writeReviewRequestDto.getRentMin();
        this.rentMax = writeReviewRequestDto.getRentMax();
        this.depositMin = writeReviewRequestDto.getDepositMin();
        this.depositMax = writeReviewRequestDto.getDepositMax();
        this.maintenanceFeeMin = writeReviewRequestDto.getMaintenanceFeeMin();
        this.maintenanceFeeMax = writeReviewRequestDto.getMaintenanceFeeMax();
        this.title = writeReviewRequestDto.getTitle();
        this.content = writeReviewRequestDto.getContent();
        this.roomType = writeReviewRequestDto.getRoomType();
        this.residence = writeReviewRequestDto.getResidence();
        this.roomStructure = writeReviewRequestDto.getRoomStructure();
        this.floor = writeReviewRequestDto.getFloor();
        this.roomSize = writeReviewRequestDto.getRoomSize();
        this.transactionType = writeReviewRequestDto.getTransactionType();

    }

    public static Review of(WriteReviewRequestDto writeReviewRequestDto) {
        Review review = new Review();
        review.maintenanceFeeMax = writeReviewRequestDto.getMaintenanceFeeMax();
        review.maintenanceFeeMin = writeReviewRequestDto.getMaintenanceFeeMin();
        review.rentMax = writeReviewRequestDto.getRentMax();
        review.rentMin = writeReviewRequestDto.getRentMin();
        review.depositMax = writeReviewRequestDto.getDepositMax();
        review.depositMin = writeReviewRequestDto.getDepositMin();
        review.transactionType = writeReviewRequestDto.getTransactionType();
        review.roomSize = writeReviewRequestDto.getRoomSize();
        review.floor = writeReviewRequestDto.getFloor();
        review.roomStructure = writeReviewRequestDto.getRoomStructure();
        review.heart = writeReviewRequestDto.getHeart();
        review.report = writeReviewRequestDto.getReport();
        review.roomType = writeReviewRequestDto.getRoomType();
        review.title = writeReviewRequestDto.getTitle();
        review.content = writeReviewRequestDto.getContent();
        review.residence = writeReviewRequestDto.getResidence();

//        review.report = report;

        return review;

    }

}



