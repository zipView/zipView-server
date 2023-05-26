package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.constants.ExceptionCode;
import zipview_server.domain.Comment;
import zipview_server.domain.Review;
import zipview_server.dto.review.*;
import zipview_server.exception.CustomException;
import zipview_server.repository.CommentRepository;
import zipview_server.repository.ReviewRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void save(WriteCommentRequestDto requestDto, Long reviewId) {

        System.out.println(reviewId);
        System.out.println(requestDto.getContent());
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));

       Comment comment = Comment.createComment(review, requestDto.getId(), requestDto.getContent());
        commentRepository.save(comment);
    }

    public CommentListResponseDto getComments(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));

        List<CommentListDto> comments = commentRepository.findAllByReview(review).stream()
                .map(comment -> CommentListDto.of(comment.getId(), comment.getContent()))
                .collect(Collectors.toList());

        return CommentListResponseDto.of(comments);
    }

    @Transactional
    public void deleteComment(Long reviewId) {

        //user 아이디 조회 후 삭제
        Comment comment = commentRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));
        commentRepository.delete(comment);
    }

    @Transactional
    public void fixComment(WriteCommentRequest request, Long reviewId) {
        Optional<Comment> comment = Optional.ofNullable(commentRepository.getComment(reviewId));

        if(comment.isEmpty()) {
            throw new CustomException(ExceptionCode.COMMENT_NOT_FOUND);
        }

        comment.ifPresent(comments -> comments.fixComment(request.getContent()));



    }
}
