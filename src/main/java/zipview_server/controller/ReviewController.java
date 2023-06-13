package zipview_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import zipview_server.api.S3Util;
import zipview_server.dto.req.Review.WriteReviewRequestDto;
import zipview_server.dto.res.Review.ReviewListResponse;
import zipview_server.dto.res.Review.ReviewListResponseDto;
import zipview_server.dto.res.Review.ReviewResponse;
import zipview_server.dto.review.*;
import zipview_server.repository.ReviewRepository;
import zipview_server.service.ReviewService;

import java.util.List;
import javax.validation.Valid;

import static zipview_server.constants.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
  //  private final S3Util s3Util;

    @GetMapping("/reviews")
    public ResponseEntity<ReviewListResponse> getReviews() {
        ReviewListResponseDto response = reviewService.getReviews();
        return ReviewListResponse.newResponse(LOAD_REVIEW_SUCCESS, response);
    }

    @PostMapping("/review")
    public ResponseEntity<ReviewResponse> saveReview(@RequestPart(value = "image", required = false) List<MultipartFile> files,
                                                     @RequestPart(value = "requestReviewDto") WriteReviewRequestDto request) throws Exception {
/* @RequestPart(value="image", required=false) List<MultipartFile> files,
    @RequestPart(value = "requestDto") BoardCreateRequestDto requestDto*/
        //WriteReviewRequestDto requestDto = request.toWriteReviewRequestDto();
        reviewService.save(request, files);
/*        List<String> imageURLs = new ArrayList<>();
        if(request.getImage().size() > 0){
            imageURLs = request.getImage().stream()
                    .map(image -> {
                        try{
                            return s3Util.postUpload(image);
                        }catch(IOException e){
                            throw new RuntimeException(e.toString());
                        }
                    })
                    .collect(Collectors.toList());
        }

        RequestReviewDto requestReviewDto = request.toRequestReviewDto(imageURLs);
        reviewService.save(requestReviewDto);
*/
        return ReviewResponse.newResponse(CREATE_REVIEW_SUCCESS);
    }

    @DeleteMapping("/review/{review-id}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable("review-id") Long reviewId) throws Exception {

        WriteReviewRequestDto writeReviewRequestDto = WriteReviewRequestDto.of(reviewId);
        reviewService.delete(writeReviewRequestDto);

        return ReviewResponse.newResponse(REVIEW_DELETE_SUCCESS);

    }

    @PutMapping("/review/change/{review-id}")
    public ResponseEntity<ReviewResponse> fixReview(@PathVariable("review-id") Long reviewId,
                                                    @RequestPart(value = "image", required = false) List<MultipartFile> files,
                                                    @RequestPart(value = "requestReviewDto") WriteReviewRequestDto writeReviewRequestDto) throws Exception {

        reviewService.fixReviews(writeReviewRequestDto, files, reviewId);

        return ReviewResponse.newResponse(REVIEW_FIX_SUCCESS);
    }

    @PostMapping("/review/report/{review-id}")
    public ResponseEntity<ReviewResponse> reportReview(@PathVariable("review-id") Long reviewId) throws Exception {

        reviewService.reportReview(reviewId);

        return ReviewResponse.newResponse(REVIEW_REPORT_SUCCESS);

    }

    @PostMapping("/review/like/{review-id}")
    public ResponseEntity<ReviewResponse> likeReview(@PathVariable("review-id") Long reviewId) throws Exception {

        reviewService.likeReview(reviewId);
        return ReviewResponse.newResponse(REVIEW_LIKE_SUCCESS);
    }

    @GetMapping("/review/best")
    public ResponseEntity<ReviewListResponse> bestReview(@PageableDefault(size = 5, sort = "likeNum", direction = Sort.Direction.DESC) Pageable pageable) {
        ReviewListResponseDto response = reviewService.getBestReviews(pageable);

        return  ReviewListResponse.newResponse(LOAD_REVIEW_BEST_SUCCESS, response);
    }


    @PostMapping("/review/filter")
    public ResponseEntity<ReviewListResponse> getFilterReview(@Valid @RequestBody RequestReviewFilterDto requestReviewFilterDto) {
        ReviewListResponseDto response = reviewService.getFilterReview(requestReviewFilterDto);

        return  ReviewListResponse.newResponse(LOAD_REVIEW_FILTER_SUCCESS, response);
    }

    @GetMapping("/home/review/search")
    public ResponseEntity<ReviewListResponse> getSerchReview(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    , @RequestParam String keyword) {
        ReviewListResponseDto response = reviewService.getSerchReviews(pageable, keyword);
        return  ReviewListResponse.newResponse(LOAD_REVIEW_HOME_SEARCH_SUCCESS, response);
    }





}

