package zipview_server.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import zipview_server.api.S3Util;
import zipview_server.constants.SuccessCode;
import zipview_server.domain.Review;
import zipview_server.dto.review.*;
import zipview_server.repository.ReviewRepository;
import zipview_server.service.ReviewService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
                                                     @RequestPart(value = "requestReviewDto") RequestReviewDto requestReviewDto) throws Exception {
/* @RequestPart(value="image", required=false) List<MultipartFile> files,
    @RequestPart(value = "requestDto") BoardCreateRequestDto requestDto*/
        reviewService.save(requestReviewDto, files);
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

        RequestReviewDto requestReviewDto = RequestReviewDto.of(reviewId);
        reviewService.delete(requestReviewDto);

        return ReviewResponse.newResponse(REVIEW_DELETE_SUCCESS);

    }

    @PutMapping("/review/change/{review-id}")
    public ResponseEntity<ReviewResponse> fixReview(@PathVariable("review-id") Long reviewId,
                                                    @RequestPart(value = "image", required = false) List<MultipartFile> files,
                                                    @RequestPart(value = "requestReviewDto") RequestReviewDto requestReviewDto) throws Exception {
     //   requestReviewDto = RequestReviewDto.of(reviewId);
       // System.out.println("가격");
     //   System.out.println(requestReviewDto.getPrice());
        reviewService.fixReviews(requestReviewDto, files, reviewId);

        return ReviewResponse.newResponse(REVIEW_FIX_SUCCESS);
    }

    @PostMapping("/review/report/{review-id}")
    public ResponseEntity<ReviewResponse> reportReview(@PathVariable("review-id") Long reviewId) throws Exception {

        reviewService.reportReview(reviewId);

        return ReviewResponse.newResponse(REVIEW_REPORT_SUCCESS);

    }





}

