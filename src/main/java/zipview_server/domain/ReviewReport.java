package zipview_server.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewReport {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    // user 연결

    public static ReviewReport createReviewReport(Review review) {
        ReviewReport reviewReport = new ReviewReport();
        reviewReport.review = review;
        //user 연결
        return reviewReport;
    }

}
