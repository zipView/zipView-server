package zipview_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipview_server.dto.req.Comment.WriteCommentRequest;
import zipview_server.dto.req.Comment.WriteCommentRequestDto;
import zipview_server.dto.res.Comment.CommentListResponse;
import zipview_server.dto.res.Comment.CommentListResponseDto;
import zipview_server.dto.res.Comment.WriteCommentResponse;
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
    public ResponseEntity<CommentListResponse> getComment(@PathVariable("review-id") Long reviewId) throws Exception {


        CommentListResponseDto responseDto = commentService.getComments(reviewId);
        return CommentListResponse.newResponse(LOAD_COMMENT_SUCCESS, responseDto);
    }

    @DeleteMapping("/comment/{review-id}") //user 연결 후 review id로 변경
    public ResponseEntity<CommentListResponse> deleteComment(@PathVariable("review-id") Long reviewId) throws Exception {
    //    CommentListRequestDto commentListRequestDto = CommentListRequestDto.of();
        commentService.deleteComment(reviewId);
        return CommentListResponse.newResponse(COMMENT_DELETE_SUCCESS);
    }

    @PutMapping("/comment/{review-id}")  //user 연결 후 review id로 변경
    public ResponseEntity<WriteCommentResponse> fixComment(@PathVariable("review-id") Long reviewId,
                                                           @Valid @RequestBody WriteCommentRequest request) throws  Exception {
        commentService.fixComment(request, reviewId);
        return WriteCommentResponse.newResponse(COMMENT_FIX_SUCCESS);
    }


    @PostMapping("/comment/report/{comment-id}")
    public ResponseEntity<CommentListResponse> reportComment(@PathVariable("comment-id") Long commentId) throws Exception {

        commentService.reportComment(commentId);

        return CommentListResponse.newResponse(COMMENT_REPORT_SUCCESS);

    }
}
