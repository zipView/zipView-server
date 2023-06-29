package zipview_server.zipview.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipview_server.config.BaseResponse;
import zipview_server.utils.PhoneRegex;
import zipview_server.zipview.sender.Dto.*;
import zipview_server.zipview.security.UserRepository;

import javax.persistence.NoResultException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static zipview_server.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
public class SendController {

    private final SendService sendService;
    private final UserRepository userRepository;


    @PostMapping("/auth/sms")
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
    @PostMapping("/auth/findpwd")
    public BaseResponse<PostEmailRes> sendEmail(@RequestBody PostEmailReq postEmailReq) throws Exception {
        String result;
        try {
            String email = userRepository.ExistUser(postEmailReq);
            if(!email.isEmpty()){ //존재하는 회원인지 판별.
                PostEmailRes postEmailRes = sendService.sendSimpleMessage(postEmailReq);
                return new BaseResponse<>(postEmailRes) ;
            }
            else{
                return new BaseResponse<>(NON_EXIST_USER_TO_EMAIL);
            }

        }catch (NoResultException e) {
            return new BaseResponse<>(NON_EXIST_MEMBER);
        }

    }

}
