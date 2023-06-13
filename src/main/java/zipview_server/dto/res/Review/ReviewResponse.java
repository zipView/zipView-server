package zipview_server.dto.res.Review;

import org.springframework.http.ResponseEntity;
import zipview_server.constants.SuccessCode;
import zipview_server.dto.BaseResponse;

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
