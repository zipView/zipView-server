package zipview_server.dto.req.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import zipview_server.domain.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
public class WriteReviewRequestDto {

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
    private RoomType roomType;
    private Residence residence;
    private Floor floor;
    private RoomSize roomSize;
    private RoomStructure roomStructure;
    private TransactionType transactionType;
    private int report;

    private WriteReviewRequestDto(Long id) {
        this.id = id;

    }

    public static WriteReviewRequestDto of(Long reviewId) {
        return new WriteReviewRequestDto(reviewId);
    }


   /* public Review toReview(Review review) {
        return Review.of(review);
    }*/

  /*  public RequestReviewDto(int price, String content, String title, List<String> images, int likenum, RoomType roomType, Residence residence) {
    }

    public RequestReviewDto(int price, String content, String title, List<String> images, int likeNum, RoomType roomType, Residence residence) {
        this.price = price;
        this.content = content;
        this.title = title;
        this.images = images;
        this.likeNum = likeNum;
        this.roomType = roomType;
        this.residence = residence;
    }
*/

/*

    public List<ReviewImage> toReviewImages(CommunityReview communityReview) {
        List<ReviewImage> reviewImages = new ArrayList<>();
        for(int i=0; i< images.size(); i++) {
            reviewImages.add(ReviewImage.createReviewImage(communityReview, false,images.get(i) ));
        }
        return reviewImages;


    }*/
}
