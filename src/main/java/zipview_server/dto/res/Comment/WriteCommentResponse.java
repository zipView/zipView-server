package zipview_server.dto.res.Comment;

import org.springframework.http.ResponseEntity;
import zipview_server.constants.SuccessCode;
import zipview_server.dto.BaseResponse;

public class WriteCommentResponse extends BaseResponse {

    private WriteCommentResponse(Boolean success, String msg) {
        super(success, msg);
    }

    public static WriteCommentResponse of(Boolean success, String msg){
        return new WriteCommentResponse(success, msg);
    }

    public static ResponseEntity<WriteCommentResponse> newResponse(SuccessCode code){
        return new  ResponseEntity(WriteCommentResponse.of(true, code.getMsg()), code.getStatus());
    }
}
