package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;


    
}
