package zipview_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import zipview_server.domain.Residence;
import zipview_server.domain.Review;
import zipview_server.domain.ReviewImage;
import zipview_server.domain.RoomType;
import java.util.List;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ReviewDto {

    private int price;
    private String content;
    private String title;
    private int likeNum;
    private List<ReviewImageDto> reviewImage;
    private RoomType roomType;
    private Residence residence;

    public ReviewDto(Review review) {
        this.price = review.getPrice();
        this.content = review.getContent();
        this.title = review.getTitle();
        this.likeNum = review.getLikeNum();
        this.reviewImage = review.getReviewImage().stream()
                .map(reviewImage -> new ReviewImageDto(reviewImage))
                .collect(Collectors.toList());
        this.roomType = review.getRoomType();
        this.residence = review.getResidence();
    }
}
