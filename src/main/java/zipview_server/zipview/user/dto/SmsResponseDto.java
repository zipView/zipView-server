package zipview_server.zipview.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class SmsResponseDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String smsConfirmNum;

    public void setSmsConfirmNum(String smsConfirmNum) {
        this.smsConfirmNum = smsConfirmNum;
    }
}
