package zipview_server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
//import zipview_server.domain.CommunityReview;
import lombok.Getter;
import zipview_server.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    private String userId;
    private String userEmail;
    private int rentMin;
    private int rentMax;
    private int depositMin;
    private int depositMax;
    private int maintenanceFeeMin;
    private int maintenanceFeeMax;
    private String title;
    private String content;
    private int likeNum;
    private List<ReviewImageDto> reviewImages;
    private RoomType roomType;
    private Residence residence;
    private Floor floor;
    private RoomSize roomSize;
    private RoomStructure roomStructure;
    private TransactionType transactionType;
    private int report;

    public ReviewDto(Review review) {
        this.id = review.getId();
     //   this.userId = review.getUser().getId();
     //   this.userEmail = review.getUser().getEmail();
        this.rentMin = review.getRentMin();
        this.rentMax = review.getRentMax();
        this.depositMin = review.getDepositMin();
        this.depositMax = review.getDepositMax();
        this.maintenanceFeeMin = review.getMaintenanceFeeMin();
        this.maintenanceFeeMax = review.getMaintenanceFeeMax();
        this.content = review.getContent();
        this.title = review.getTitle();
        this.likeNum = review.getLikeNum();
        this.reviewImages = review.getReviewImages().stream()
               .map(reviewImage -> new ReviewImageDto(reviewImage))
               .collect(Collectors.toList());
        this.roomType = review.getRoomType();
        this.residence = review.getResidence();
        this.floor = review.getFloor();
        this.roomSize = review.getRoomSize();
        this.roomStructure = review.getRoomStructure();
        this.transactionType = review.getTransactionType();
        this.report = review.getReport();
    }



}
