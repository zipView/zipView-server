package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.api.FileHandler;
//import zipview_server.domain.CommunityReview;
import zipview_server.constants.ExceptionCode;
import zipview_server.domain.*;
import zipview_server.dto.review.*;
//import zipview_server.repository.CommunityReviewRepository;
import zipview_server.exception.CustomException;
import zipview_server.repository.*;

import java.util.ArrayList;
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
    private final LikeReviewRepository likeReviewRepository;
    private final FilterRepository filterRepository;

    @Transactional
    public void save(RequestReviewDto requestReviewDto, List<MultipartFile> files) throws Exception {
   //     User user = getUser();
        Review review = Review.createReivew(requestReviewDto.getRentMin(), requestReviewDto.getRentMax(), requestReviewDto.getDepositMin(), requestReviewDto.getDepositMax(), requestReviewDto.getMaintenanceFeeMin(), requestReviewDto.getMaintenanceFeeMax(), requestReviewDto.getContent(), requestReviewDto.getTitle(), requestReviewDto.getLikeNum(), requestReviewDto.getRoomType(), requestReviewDto.getResidence(), requestReviewDto.getReport(), requestReviewDto.getFloor(), requestReviewDto.getRoomSize(), requestReviewDto.getRoomStructure(), requestReviewDto.getTransactionType());
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
        review.ifPresent(reviews -> reviews.fixReview(requestReviewDto.getRentMin(), requestReviewDto.getRentMax(), requestReviewDto.getDepositMin(), requestReviewDto.getDepositMax(), requestReviewDto.getMaintenanceFeeMin(), requestReviewDto.getMaintenanceFeeMax(), requestReviewDto.getContent(), requestReviewDto.getTitle(), requestReviewDto.getRoomType(), requestReviewDto.getResidence(), requestReviewDto.getFloor(), requestReviewDto.getRoomSize(), requestReviewDto.getRoomStructure(), requestReviewDto.getTransactionType()));


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

    @Transactional
    public String likeReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                        .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        //유저 연결

        if(likeReviewRepository.findByReview(review) == null) {

            review.setLikeNum(review.getLikeNum()+1);
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
        Page<Review> reviews = reviewRepository.findByLikeNumGreaterThanEqual(pageable, 10);
        List<ReviewDto> reviewList = new ArrayList<>();
      //  reviews.stream().map(review -> new ReviewDto(review))
       //         .collect(Collectors.toList());
        reviews.stream().forEach(i -> reviewList.add(new ReviewDto(i)));

       // System.out.println(reviewList.get(0).getLikeNum());
        return ReviewListResponseDto.of(reviewList);
    }

    public ReviewListResponseDto getFilterReview(RequestReviewFilterDto requestReviewFilterDto) {

        List<ReviewDto> reviews = filterRepository.getReview(requestReviewFilterDto.getRentMin(), requestReviewFilterDto.getRentMax(), requestReviewFilterDto.getDepositMin(), requestReviewFilterDto.getDepositMax(), requestReviewFilterDto.getMaintenanceFeeMin(), requestReviewFilterDto.getMaintenanceFeeMax(), requestReviewFilterDto.getRoomType(), requestReviewFilterDto.getFloor(), requestReviewFilterDto.getRoomSize(), requestReviewFilterDto.getRoomStructure(), requestReviewFilterDto.getTransactionType())
                .stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());

        return ReviewListResponseDto.of(reviews);


        //roomType, @Param("floor") Floor floor, @Param("roomSize") RoomSize roomSize, @Param("roomStructure") RoomStructure roomStructure
    }


//    public ReviewListResponseDto getReviews() {
//        List<ReviewDto> reviews = reviewRepository.findAll().stream()
//                .map(review -> new ReviewDto(review))
//                .collect(Collectors.toList());
//        return ReviewListResponseDto.of(reviews);
//    }
}



/*
    private User getUser() {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_EMAIL_NOT_FOUND));
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ExceptionCode.MEMBER_EMAIL_NOT_FOUND));
    }*/