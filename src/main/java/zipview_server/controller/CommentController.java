package zipview_server.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipview_server.dto.review.*;
import zipview_server.service.CommentService;

import javax.validation.Valid;

import static zipview_server.constants.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{review-id}")
    public ResponseEntity<WriteCommentResponse> saveComment(@PathVariable("review-id") Long reviewId,
                                                            @Valid @RequestBody WriteCommentRequest request) {
        WriteCommentRequestDto requestDto = request.toWriteCommentRequestDto();
       // System.out.println(requestDto.getReviewId());
        System.out.println(requestDto.getContent());
        commentService.save(requestDto, reviewId);

        return WriteCommentResponse.newResponse(CREATE_COMMENT_SUCCESS);
    }

    @GetMapping("/comments/{review-id}")
    public ResponseEntity<CommentListResponse> getCommet(@PathVariable("review-id") Long reviewId) throws Exception {


        CommentListResponseDto responseDto = commentService.getComments(reviewId);
        return CommentListResponse.newResponse(LOAD_COMMENT_SUCCESS, responseDto);
    }
}
