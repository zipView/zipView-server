package zipview_server.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.constants.ExceptionCode;
import zipview_server.domain.Comment;
import zipview_server.domain.CommentReport;
import zipview_server.domain.Review;
import zipview_server.dto.req.Comment.WriteCommentRequest;
import zipview_server.dto.req.Comment.WriteCommentRequestDto;
import zipview_server.dto.res.Comment.CommentListResponseDto;
import zipview_server.dto.review.*;
import zipview_server.exception.CustomException;
import zipview_server.repository.CommentReportRepository;
import zipview_server.repository.CommentRepository;
import zipview_server.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static zipview_server.constants.ExceptionCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final CommentReportRepository commentReportRepository;

    @Transactional
    public void save(WriteCommentRequestDto requestDto, Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));

       Comment comment = Comment.createComment(review, requestDto.getId(), requestDto.getContent(),  LocalDateTime.now(), requestDto.getReport());
        commentRepository.save(comment);
    }

    public CommentListResponseDto getComments(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ExceptionCode.REVIEW_NOT_FOUND));

        List<CommentListDto> comments = commentRepository.findAllByReview(review).stream()
                .map(comment -> CommentListDto.of(comment.getId(), comment.getContent(), comment.getCreateTime(), comment.getReport()))
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

    @Transactional
    public void reportComment(Long commentId) {
        //user 연결

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));


        Optional<CommentReport> commentReport = commentReportRepository.getCommentReport(commentId);

        if(commentReport.isEmpty()) {
            CommentReport report = CommentReport.createCommentReport(comment);
            commentReportRepository.save(report);

            comment.increaseReport();
            if(comment.getReport() == 10) {

                commentReportRepository.deleteCommentReport(comment.getId());
                commentRepository.delete(comment);

            }

        }
        if (commentReport.isPresent()) {
            throw new CustomException(ALREADY_REPORT_COMMENT);
        }

    }
}

