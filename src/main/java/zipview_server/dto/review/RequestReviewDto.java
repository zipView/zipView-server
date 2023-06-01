package zipview_server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import zipview_server.domain.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestReviewDto {

    private Long id;
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

    private RequestReviewDto(Long id) {
        this.id = id;

    }

    public static RequestReviewDto of(Long reviewId) {
        return new RequestReviewDto(reviewId);
    }


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
    public CommunityReview toCommunityReivew() {
        return CommunityReview.builder()
                .price(price)
                .content(content)
                .title(title)
                .likeNum(likeNum)
                .roomType(roomType)
                .residence(residence)
                .build();
    }
    public List<ReviewImage> toReviewImages(CommunityReview communityReview) {
        List<ReviewImage> reviewImages = new ArrayList<>();
        for(int i=0; i< images.size(); i++) {
            reviewImages.add(ReviewImage.createReviewImage(communityReview, false,images.get(i) ));
        }
        return reviewImages;


    }*/
}
