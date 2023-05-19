package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.domain.Review;
import zipview_server.repository.ReviewRepository;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long save(Review review) {
        reviewRepository.save(review);
        return review.getId();
    }

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

}
