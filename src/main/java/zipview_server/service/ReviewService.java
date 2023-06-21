package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.api.FileHandler;
import zipview_server.domain.*;
import zipview_server.dto.req.Review.WriteReviewRequestDto;
import zipview_server.dto.res.Review.ReviewListResponseDto;
import zipview_server.dto.review.*;
import zipview_server.exception.CustomException;
import zipview_server.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static zipview_server.constants.ExceptionCode.*;

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
    private final LikeReviewRepository likeReviewRepository;
    private final FilterRepository filterRepository;

    @Transactional
    public void save(WriteReviewRequestDto writeReviewRequestDto, List<MultipartFile> files) throws Exception {

    //    User user = userReviewRepository.findByIdAndEmail(requestReviewDto.getUserId(), requestReviewDto.getUserEmail())
     //           .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Review review = Review.of(writeReviewRequestDto);

        List<ReviewImage> reviewImageList = fileHandler.parseFileInfo(files);
        if(!reviewImageList.isEmpty()) {
            for (ReviewImage reviewImage : reviewImageList) {
                review.addReviewImage(reviewImage);

            }
        }

    /* 사진
     CommunityReview communityReview = requestReviewDto.toCommunityReivew();
          communityReviewRepository.save(communityReview);
       List<ReviewImage> reviewImages = requestReviewDto.toReviewImages(communityReview);
       reviewImageRepository.saveAll(reviewImages);*/

        reviewRepository.save(review);
    }

    @Transactional
    public void delete(WriteReviewRequestDto writeReviewRequestDto) {
        Review review = reviewRepository.findById(writeReviewRequestDto.getId())
                .orElseThrow(()-> new CustomException(REVIEW_NOT_FOUND));
        reviewRepository.delete(review);

    }

    //리뷰 전체보기
    public ReviewListResponseDto getReviews() {
      //  User user = getUser();
        List<ReviewDto> reviews = reviewRepository.findAll().stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
        return ReviewListResponseDto.of(reviews);
    }

    @Transactional
    public void fixReviews(WriteReviewRequestDto writeReviewRequestDto, List<MultipartFile> files, Long reviewId) throws Exception {

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
                reviewImage.setReview(review.get());
            }
        }

        review.ifPresent(reviews -> reviews.fixReview(writeReviewRequestDto));

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
         if (reviewReport.isPresent()) {
            throw new CustomException(ALREADY_REPORT_REVIEW);
        }
    }

    @Transactional
    public String likeReview(Long reviewId) { //좋아요 완료, 취소 분리 ?
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        //유저 연결
        if(likeReviewRepository.findByReview(review) == null) {
            review.setHeart(review.getHeart()+1);
            LikeReview likeReview = LikeReview.createLikeReview(review);
            likeReviewRepository.save(likeReview);
            return "좋아요 완료";
        } else {
            LikeReview likeReview = likeReviewRepository.findByReview(review);
            likeReview.unLikeReview(review);
            likeReviewRepository.delete(likeReview);
            return "좋아요 취소 완료";
        }
    }

    public ReviewListResponseDto getBestReviews(Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByHeartGreaterThanEqual(pageable, 10);
        List<ReviewDto> reviewList = new ArrayList<>();
      //  reviews.stream().map(review -> new ReviewDto(review))
       //         .collect(Collectors.toList());
        reviews.stream().forEach(i -> reviewList.add(new ReviewDto(i)));

       // System.out.println(reviewList.get(0).getLike());
        return ReviewListResponseDto.of(reviewList);
    }

    public ReviewListResponseDto getFilterReview(RequestReviewFilterDto requestReviewFilterDto) {
    //refec
        List<ReviewDto> reviews = filterRepository.getReview(requestReviewFilterDto.getRentMin(), requestReviewFilterDto.getRentMax(), requestReviewFilterDto.getDepositMin(), requestReviewFilterDto.getDepositMax(), requestReviewFilterDto.getMaintenanceFeeMin(), requestReviewFilterDto.getMaintenanceFeeMax(), requestReviewFilterDto.getRoomType(), requestReviewFilterDto.getFloor(), requestReviewFilterDto.getRoomSize(), requestReviewFilterDto.getRoomStructure(), requestReviewFilterDto.getTransactionType())
                .stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());

        return ReviewListResponseDto.of(reviews);
    }

    public ReviewListResponseDto getSerchReviews(Pageable pageable, String keyword) {
        Page<Review> reviews = reviewRepository.findAllReviewSerch(pageable, keyword);
        List<ReviewDto> reviewList = new ArrayList<>();
        reviews.stream().forEach(i -> reviewList.add(new ReviewDto(i)));

        return ReviewListResponseDto.of(reviewList);
    }


}


