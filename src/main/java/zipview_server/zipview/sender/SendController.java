package zipview_server.zipview.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipview_server.config.BaseResponse;
import zipview_server.utils.PhoneRegex;
import zipview_server.zipview.sender.Dto.Mail;
import zipview_server.zipview.sender.Dto.MessageDto;
import zipview_server.zipview.sender.Dto.PostEmailReq;
import zipview_server.zipview.sender.Dto.SmsResponseDto;
import zipview_server.zipview.user.UserRepository;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static zipview_server.config.BaseResponseStatus.INVALID_PHONE_NUMBER;

@RequiredArgsConstructor
@RestController
public class SendController {

    private final SendService sendService;


    @PostMapping("/sms/send")
    public BaseResponse<SmsResponseDto> sendSms(@RequestBody MessageDto messageDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException,  InvalidKeyException, JsonProcessingException {
        if(!PhoneRegex.isRegexPhone(messageDto.getTo())){
            return new BaseResponse<>(INVALID_PHONE_NUMBER);
        }
        SmsResponseDto responseDto = sendService.sendSms(messageDto);
        return new BaseResponse<>(responseDto);
    }

    /**
     * 임시 비밀번호 메일 보내기
     * SMTP
     */
    @PostMapping("/email/send")
    public String sendEmail(@RequestBody PostEmailReq postEmailReq) throws Exception {
        String code = sendService.sendSimpleMessage(postEmailReq);
        return code;

    }

}
