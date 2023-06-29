package zipview_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zipview_server.constants.ExceptionCode;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ExceptionCode exceptionCode;
}
