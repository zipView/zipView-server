package zipview_server.zipview.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.mail.MailException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import zipview_server.utils.Decrypt;
import zipview_server.utils.Encrypt;
import zipview_server.zipview.sender.Dto.*;
import zipview_server.zipview.user.UserRepository;
import zipview_server.zipview.user.dto.User;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class SendService {
    private final String smsConfirmNum = createSmsKey();
    private final JavaMailSender javaMailSender;
    private final String ePw = getTempPwd();
    private final UserRepository userRepository;

    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;

    @Value("${spring.mail.username}")
    private String from_address;


    public String getSignature(String time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
        System.out.println("encodeBase64 = " + encodeBase64String);
        return encodeBase64String;
    }
    public SmsResponseDto sendSms(MessageDto messageDto) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String time = Long.toString(System.currentTimeMillis());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time);
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", getSignature(time)); // signature 서명

        List<MessageDto> messages = new ArrayList<>();
        messages.add(messageDto);

        SmsRequestDto request = SmsRequestDto.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content("[집뷰] 인증번호 [" + smsConfirmNum + "] 를 입력해주세요")
                .messages(messages)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);
        HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResponseDto smsResponseDto = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/"+ serviceId +"/messages"), httpBody,SmsResponseDto.class);
        smsResponseDto.setSmsConfirmNum(smsConfirmNum);
        return smsResponseDto;
    }



    // 인증코드 만들기
    public static String createSmsKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 5; i++) { // 인증코드 5자리
            key.append((rnd.nextInt(10)));
        }
        return key.toString();
    }

    @Transactional
    public PostEmailRes sendSimpleMessage(PostEmailReq postEmailReq)throws Exception {
        MimeMessage message = createMessage(postEmailReq.getEmail());
        try{
            javaMailSender.send(message); // 메일 발송
            String newPwd = Encrypt.encryptAES256(ePw);

            String id = userRepository.findId(postEmailReq.getName(), postEmailReq.getEmail());
            User user = userRepository.findOne(id);
            user.setPassword(newPwd);
            userRepository.save(user);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        PostEmailRes result = new PostEmailRes(ePw);
        return result; // 메일로 보냈던 인증 코드를 서버로 리턴
    }
    public MimeMessage createMessage(String to)throws MessagingException, UnsupportedEncodingException {
        MimeMessage  message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
        message.setSubject("[ZIPVIEW] 임시 비밀번호 안내 이메일 입니다."); //메일 제목

        String msg="";
        msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">임시 비밀번호가 설정 되었습니다. 로그인 후에 비밀번호를 변경 해 주세요.</p>";
        msg += "<div style=\"padding: 50px; margin: 32px 32px 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
        msg += ePw;
        msg += "</td></tr></tbody></table></div>";

        message.setText(msg, "utf-8", "html"); //내용, charset타입, subtype
        message.setFrom(new InternetAddress(from_address,"ZIPVIEW")); //보내는 사람의 메일 주소, 보내는 사람 이름

        return message;
    }


    public String getTempPwd() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
}
