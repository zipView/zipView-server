package zipview_server.constants;

import static org.springframework.http.HttpStatus.OK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    LOAD_REVIEW_SUCCESS(OK, "전체 리뷰 조회에 성공하셨습니다."),
    CREATE_REVIEW_SUCCESS(OK, "리뷰 생성에 성공하셨습니다."),
    REVIEW_DELETE_SUCCESS(OK, "리뷰 삭제에 성공하셨습니다."),
    REVIEW_FIX_SUCCESS(OK, "리뷰 수정에 성공하셨습니다."),
    REVIEW_REPORT_SUCCESS(OK, "리뷰 신고에 성공하셨습니다."),
    REVIEW_LIKE_SUCCESS(OK, "리뷰 좋아요에 성공하셨습니다."),
    REVIEW_UNLIKE_SUCCESS(OK, "리뷰 좋아요 취소에 성공하셨습니다."),
    LOAD_REVIEW_BEST_SUCCESS(OK, "인기있는 리뷰 조회에 성공하셨습니다."),
    LOAD_REVIEW_HOME_SEARCH_SUCCESS(OK, "홈 화면 리뷰 조회에 성공하셨습니다."),
    LOAD_REVIEW_FILTER_SUCCESS(OK, "필터링 리뷰 조회에 성공하셨습니다."),


    LOAD_COMMENT_SUCCESS(OK, "코멘트 조회에 성공하셨습니다."),
    CREATE_COMMENT_SUCCESS(OK, "코멘트 생성에 성공하셨습니다."),
    COMMENT_DELETE_SUCCESS(OK, "코멘트 삭제에 성공하셨습니다."),
    COMMENT_FIX_SUCCESS(OK, "코멘트 수정에 성공하셨습니다."),
    COMMENT_REPORT_SUCCESS(OK, "코멘트 신고에 성공하셨습니다.")
    ;


   // LOAD_REVIEW_SUCCES(OK, "리뷰 조회에 성공하셨습니다.");
    private final HttpStatus status;
    private final String msg;
}