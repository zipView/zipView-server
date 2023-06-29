package zipview_server.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.domain.Review;
import zipview_server.ZipviewApplication;


@SpringBootTest(classes = ZipviewApplication.class)
@ContextConfiguration(classes = ZipviewApplication.class)
@Transactional
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void testReview() {
    //    Review review = new Review(400,"ss");
     //   Review savedReview = reviewRepository.save(review);

       // Review findReview = reviewRepository.findById(savedReview.getId()).get();

      //  Assertions.assertThat(findReview.getId()).isEqualTo(review.getId());
      //  Assertions.assertThat(findReview.getPrice()).isEqualTo(review.getPrice());
      //  Assertions.assertThat(findReview.getContent()).isEqualTo(review.getContent());
      //  Assertions.assertThat(findReview).isEqualTo(review);
    }

}