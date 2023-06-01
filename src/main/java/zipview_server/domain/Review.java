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

    private int likeNum;

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

    public static Review createReivew(int rentMin, int rentMax,  int depositMin, int depositMax, int maintenanceFeeMin, int maintenanceFeeMax, String content, String title, int likeNum, RoomType roomType, Residence residence, int report, Floor floor, RoomSize roomSize, RoomStructure roomStructure, TransactionType transactionType) {
        Review review = new Review();
  //      review.id = id;
    //    review.user = user;
        review.rentMin = rentMin;
        review.rentMax = rentMax;
        review.depositMin = depositMin;
        review.depositMax = depositMax;
        review.maintenanceFeeMin = maintenanceFeeMin;
        review.maintenanceFeeMax = maintenanceFeeMax;
        review.title = title;
        review.content = content;
        review.likeNum = likeNum;
        review.roomType = roomType;
        review.residence = residence;
        review.report = report;
        review.floor = floor;
        review.roomSize = roomSize;
        review.roomStructure = roomStructure;
        review.transactionType = transactionType;
        return review;
    }

    public void fixReview(int rentMin, int rentMax,  int depositMin, int depositMax, int maintenanceFeeMin, int maintenanceFeeMax, String content, String title, RoomType roomType, Residence residence,  Floor floor, RoomSize roomSize, RoomStructure roomStructure, TransactionType transactionType) {

        this.rentMin = rentMin;
        this.rentMax = rentMax;
        this.depositMin = depositMin;
        this.depositMax = depositMax;
        this.maintenanceFeeMin = maintenanceFeeMin;
        this.maintenanceFeeMax = maintenanceFeeMax;
        this.title = title;
        this.content = content;
        this.roomType = roomType;
        this.residence = residence;
        this.roomStructure = roomStructure;
        this.floor = floor;
        this.roomSize = roomSize;
        this.transactionType = transactionType;

    }


}



