package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.api.FileHandler;
//import zipview_server.domain.CommunityReview;
import zipview_server.constants.ExceptionCode;
import zipview_server.domain.Residence;
import zipview_server.domain.Review;
import zipview_server.domain.ReviewImage;
import zipview_server.dto.review.RequestReviewDto;
import zipview_server.dto.review.ReviewDto;
//import zipview_server.repository.CommunityReviewRepository;
import zipview_server.dto.review.ReviewListResponseDto;
import zipview_server.exception.CustomException;
import zipview_server.repository.ReviewImageRepository;
import zipview_server.repository.ReviewRepository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
  // private final CommunityReviewRepository communityReviewRepository;
    private final FileHandler fileHandler;

    @Transactional
    public void save(RequestReviewDto requestReviewDto, List<MultipartFile> files) throws Exception {
        Review review = Review.createReivew(requestReviewDto.getPrice(), requestReviewDto.getContent(), requestReviewDto.getTitle(), requestReviewDto.getLikeNum(), requestReviewDto.getRoomType(), requestReviewDto.getResidence());
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
                .orElseThrow(()-> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));
        reviewRepository.delete(review);

    }


    //리뷰 전체보기
    public ReviewListResponseDto getReviews() {
        List<ReviewDto> reviews = reviewRepository.findAll().stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());
        return ReviewListResponseDto.of(reviews);
    }

}
