package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.api.FileHandler;
//import zipview_server.domain.CommunityReview;
import zipview_server.constants.ExceptionCode;
import zipview_server.domain.Residence;
import zipview_server.domain.Review;
import zipview_server.domain.ReviewImage;
import zipview_server.domain.ReviewReport;
import zipview_server.dto.review.RequestReviewDto;
import zipview_server.dto.review.ReviewDto;
//import zipview_server.repository.CommunityReviewRepository;
import zipview_server.dto.review.ReviewListResponseDto;
import zipview_server.dto.review.ReviewResponse;
import zipview_server.exception.CustomException;
import zipview_server.repository.ReviewImageRepository;
import zipview_server.repository.ReviewReportRepository;
import zipview_server.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static zipview_server.constants.ExceptionCode.ALREADY_REPORT_REVIEW;
import static zipview_server.constants.ExceptionCode.REVIEW_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
//    private final UserRepository userRepository;
  // private final CommunityReviewRepository communityReviewRepository;
    private final ReviewReportRepository reviewReportRepository;
    private final FileHandler fileHandler;

    @Transactional
    public void save(RequestReviewDto requestReviewDto, List<MultipartFile> files) throws Exception {
   //     User user = getUser();
        Review review = Review.createReivew(requestReviewDto.getPrice(), requestReviewDto.getContent(), requestReviewDto.getTitle(), requestReviewDto.getLikeNum(), requestReviewDto.getRoomType(), requestReviewDto.getResidence(), requestReviewDto.getReport());
        List<ReviewImage> reviewImageList = fileHandler.parseFileInfo(files);
        if(!reviewImageList.isEmpty()) {
            for (ReviewImage reviewImage : reviewImageList) {
                review.addReviewImage(reviewImage);
            }
        }
    /*    CommunityReview communityReview = requestReviewDto.toCommunityReivew();
          communityReviewRepository.save(communityReview);
       List<ReviewImage> reviewImages = requestReviewDto.toReviewImages(communityReview);
       reviewImageRepository.saveAll(reviewImages);*/
        reviewRepository.save(review);
    }

    @Transactional
    public void delete(RequestReviewDto requestReviewDto) {
        Review review = reviewRepository.findById(requestReviewDto.getId())
                .orElseThrow(()-> new CustomException(REVIEW_NOT_FOUND));
        reviewRepository.delete(review);

    }

    //리뷰 전체보기
    public ReviewListResponseDto getReviews() {
        List<ReviewDto> reviews = reviewRepository.findAll().stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
        return ReviewListResponseDto.of(reviews);
    }

    @Transactional
    public void fixReviews(RequestReviewDto requestReviewDto, List<MultipartFile> files, Long reviewId) throws Exception {

      //  Review review = reviewRepository.findById(requestReviewDto.getId())
       ///         .orElseThrow(()-> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));

        Optional<Review> review = Optional.ofNullable(reviewRepository.getReview(reviewId));
        if (review.isEmpty()) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }
        System.out.println(review.get().getContent());
        List<ReviewImage> reviewImageList = fileHandler.parseFileInfo(files);
        if(!reviewImageList.isEmpty()) {
            for (ReviewImage reviewImage : reviewImageList) {
                System.out.println("라뷰 추가한다");

                System.out.println(review.get().getContent());
                reviewImage.setReview(review.get());
            }
        }
        System.out.println(requestReviewDto.getPrice());
        review.ifPresent(reviews -> reviews.fixReview(requestReviewDto.getPrice(), requestReviewDto.getContent(), requestReviewDto.getTitle(), requestReviewDto.getRoomType(), requestReviewDto.getResidence()));


    }

    @Transactional
    public void reportReview(Long reviewId) {
        //user 연결
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        Optional<ReviewReport> reviewReport = reviewReportRepository.getReviewReport(reviewId);

        if(reviewReport.isEmpty()) {
            ReviewReport report = ReviewReport.createReviewReport(review);
            reviewReportRepository.save(report);

            review.increaseReport();
            if(review.getReport() == 10) {

                reviewReportRepository.deleteReviewReport(review.getId());
                reviewRepository.delete(review);

            }

        }
       /* if (reviewReport.isPresent()) {
            throw new CustomException(ALREADY_REPORT_REVIEW);
        }*/

    }
}
/*
    private User getUser() {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_EMAIL_NOT_FOUND));
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_EMAIL_NOT_FOUND));
    }*/