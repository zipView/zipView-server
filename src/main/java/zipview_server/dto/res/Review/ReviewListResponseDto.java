package zipview_server.dto.res.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.dto.review.ReviewDto;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewListResponseDto {

    List<ReviewDto> reviews;

    private ReviewListResponseDto(List<ReviewDto> reviews) {
        this.reviews = reviews;
    }

    public static ReviewListResponseDto of(List<ReviewDto> reviews) {
        return new ReviewListResponseDto(reviews);
    }

}
