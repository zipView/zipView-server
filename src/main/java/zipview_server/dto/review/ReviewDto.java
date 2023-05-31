package zipview_server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
//import zipview_server.domain.CommunityReview;
import lombok.Getter;
import zipview_server.domain.Residence;
import zipview_server.domain.Review;
import zipview_server.domain.RoomType;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Data
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    private int price;
    private String title;
    private String content;
    private int likeNum;
    private List<ReviewImageDto> reviewImages;
    private RoomType roomType;
    private Residence residence;
    private int report;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.price = review.getPrice();
        this.content = review.getContent();
        this.title = review.getTitle();
        this.likeNum = review.getLikeNum();
        this.reviewImages = review.getReviewImages().stream()
               .map(reviewImage -> new ReviewImageDto(reviewImage))
               .collect(Collectors.toList());
        this.roomType = review.getRoomType();
        this.residence = review.getResidence();
        this.report = review.getReport();
    }



}
