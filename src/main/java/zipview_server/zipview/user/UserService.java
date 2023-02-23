package zipview_server.zipview.user;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import zipview_server.config.BaseException;
import zipview_server.utils.Encrypt;
import zipview_server.zipview.user.dto.*;


import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;

import static zipview_server.config.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    final UserRepository userRepository;
    @Value("${naver-login.secretkey}")
    private String secretKey;
    @Value("${naver-login.clientId}")
    private String clientId;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public String join(User user) throws BaseException {
        validateDuplicateMember(user);
        validateExistNickname(user);
        String pwd;
        try {
            pwd = Encrypt.encryptAES256(user.getPassword());
            user.setPassword(pwd);
        } catch (Exception ignored) {
            throw new BaseException(FAIL_ENCRYPT_PWD);
        }
        userRepository.save(user);
        return user.getId();
    }
    @Transactional
    public String socialJoin(User user) throws BaseException {
        validateDuplicateMember(user);
        validateExistNickname(user);
        userRepository.save(user);
        return user.getId();
    }

    public User socialLogin(String code, String state) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders accessTokenHeaders = new HttpHeaders();
        accessTokenHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
        accessTokenParams.add("grant_type", "authorization_code");
        accessTokenParams.add("client_id", clientId);
        accessTokenParams.add("client_secret", secretKey);
        accessTokenParams.add("code" , code);
        accessTokenParams.add("state" , state);

        HttpEntity<MultiValueMap<String, String>> accessTokenRequest
                = new HttpEntity<>(accessTokenParams, accessTokenHeaders);

        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                accessTokenRequest,
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        NaverOauthParams naverOauthParams = null;
        try {
            naverOauthParams = objectMapper.readValue(accessTokenResponse.getBody(), NaverOauthParams.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpHeaders profileRequestHeader = new HttpHeaders();
        profileRequestHeader.add("Authorization", "Bearer " + naverOauthParams.getAccess_token());

        HttpEntity<HttpHeaders> profileHttpEntity = new HttpEntity<>(profileRequestHeader);

        ResponseEntity<String> profileResponse = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                profileHttpEntity,
                String.class
        );

        JsonElement element = JsonParser.parseString(profileResponse.getBody());

        String email = element.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
        String nickname= element.getAsJsonObject().get("response").getAsJsonObject().get("nickname").getAsString();
        String id = element.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
        String name = element.getAsJsonObject().get("response").getAsJsonObject().get("name").getAsString();
        String phone = element.getAsJsonObject().get("response").getAsJsonObject().get("mobile").getAsString();
        User user = new User(email,nickname,id,name,phone);
        log.info(String.valueOf(user));
        return user;
    }


    private void validateDuplicateMember(User user) throws BaseException {
        List<User> findUser = userRepository.findByEmail(user.getEmail());
        if(!findUser.isEmpty()) {
            throw new BaseException(EXIST_EMAIL);
        }
    }
    private void validateExistNickname(User user) throws BaseException {
        List<User> findUser = userRepository.findByNickName(user.getNickname());
        if(!findUser.isEmpty()){
            throw new BaseException(EXIST_NICKNAME);
        }
    }

}
