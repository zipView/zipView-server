package zipview_server.constants;

import static org.springframework.http.HttpStatus.OK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    CREATE_REVIEW_SUCCESS(OK, "리뷰 생성에 성공하셨습니다.");

    private final HttpStatus status;
    private final String msg;
}