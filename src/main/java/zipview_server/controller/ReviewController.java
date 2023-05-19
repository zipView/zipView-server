package zipview_server.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipview_server.domain.Review;
import zipview_server.repository.ReviewRepository;
import zipview_server.service.ReviewService;
import java.util.List;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping("/reviews")
    public List<Review> findReview() {
        return reviewService.findReviews();

    }

    @PostMapping("/review")
    public CreateReviewResponse saveReview(@RequestBody @Valid CreateReviewRequest) {
        Long id = reviewService.save(review); ///
        return new CreateReviewResponse(id);
    }

    @Data
    static class CreateReviewResponse {
        private Long id;

        public CreateReviewResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class CreateReviewRequest {
        private int price;
        private String content;
    }
}

