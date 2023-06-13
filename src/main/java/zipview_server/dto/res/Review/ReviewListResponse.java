package zipview_server.dto.res.Review;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import zipview_server.constants.SuccessCode;
import zipview_server.dto.BaseResponse;

@Getter
public class ReviewListResponse extends BaseResponse {
    ReviewListResponseDto data;

    private ReviewListResponse(Boolean success, String msg, ReviewListResponseDto data) {
        super(success, msg);
        this.data = data;
    }

    public static ReviewListResponse of(Boolean success, String message, ReviewListResponseDto data) {
        return new ReviewListResponse(success, message, data);
    }

    public static ResponseEntity<ReviewListResponse> newResponse(SuccessCode code, ReviewListResponseDto data) {
        ReviewListResponse response = ReviewListResponse.of(true, code.getMsg(), data);
        return new ResponseEntity<ReviewListResponse>(response, code.getStatus());
    }

}
