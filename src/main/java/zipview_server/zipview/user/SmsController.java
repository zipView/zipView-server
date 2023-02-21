package zipview_server.zipview.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import zipview_server.utils.PhoneRegex;
import zipview_server.utils.PwdRegex;
import zipview_server.zipview.user.SmsService;
import zipview_server.zipview.user.dto.MessageDto;
import zipview_server.zipview.user.dto.SmsResponseDto;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static zipview_server.config.BaseResponseStatus.INVALID_PHONE_NUMBER;
import static zipview_server.config.BaseResponseStatus.INVALID_PWD;

@RequiredArgsConstructor
@RestController
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/sms/send")
    public BaseResponse<SmsResponseDto> sendSms(@RequestBody MessageDto messageDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException,  InvalidKeyException, JsonProcessingException {
        if(!PhoneRegex.isRegexPhone(messageDto.getTo())){
            return new BaseResponse<>(INVALID_PHONE_NUMBER);
        }
        SmsResponseDto responseDto = smsService.sendSms(messageDto);
        return new BaseResponse<>(responseDto);
    }
}
