package zipview_server.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zipview_server.constants.ExceptionCode;
import zipview_server.constants.SuccessCode;
import zipview_server.dto.BaseResponse;

import java.time.LocalDateTime;

public class ReviewResponse extends BaseResponse {

    private ReviewResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static ReviewResponse of(Boolean success, String msg) {
        return new ReviewResponse(success, msg);
    }

    public static  ResponseEntity<ReviewResponse> newResponse(SuccessCode code) {
        return new ResponseEntity(ReviewResponse.of(true, code.getMsg()), code.getStatus());
    }











}
