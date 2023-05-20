package zipview_server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zipview_server.constants.ExceptionCode;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BaseResponse {

    private boolean success;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public BaseResponse(Boolean success, String msg) {
        this.success = success;
        this.message = msg;
    }

    public static BaseResponse of(Boolean success, String msg) {
        return new BaseResponse(success, msg);
    }

    public static ResponseEntity<BaseResponse> toCustomErrorResponse(ExceptionCode exceptionCode) {
        return ResponseEntity
                .status(exceptionCode.getStatus())
                .body(BaseResponse.of(false, exceptionCode.getMsg()));
    }

    public static ResponseEntity<BaseResponse> toBasicErrorResponse(HttpStatus status, String msg) {
        return ResponseEntity
                .status(status)
                .body(BaseResponse.of(false, msg));
    }
}