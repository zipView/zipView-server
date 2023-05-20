package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.domain.Residence;
import zipview_server.domain.Review;
import zipview_server.domain.ReviewImage;
import zipview_server.dto.review.RequestReviewDto;
import zipview_server.dto.review.ReviewDto;
import zipview_server.repository.ReviewRepository;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = false)
    public void save(RequestReviewDto requestReviewDto) {
        ReviewImage reviewImage = ReviewImage.createReviewImage(r);
        Review review = Review.createReview(requestReviewDto.getPrice(), requestReviewDto.getContent(), requestReviewDto.getTitle(), requestReviewDto.getReviewImage(), requestReviewDto.getLikeNum(), requestReviewDto.getRoomType(), requestReviewDto.getResidence());
        reviewRepository.save(review);
    }

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

}
