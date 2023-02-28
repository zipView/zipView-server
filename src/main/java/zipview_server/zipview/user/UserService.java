package zipview_server.zipview.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import zipview_server.utils.Decrypt;
import zipview_server.utils.Encrypt;
import zipview_server.utils.PwdRegex;
import zipview_server.zipview.user.dto.*;


import java.util.List;

import static zipview_server.config.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${naver-login.secretkey}")
    private String secretKey;
    @Value("${naver-login.clientId}")
    private String clientId;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
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

    @Transactional
    public PostLoginRes login(PostLoginReq postLoginReq) throws Exception {
        String repoPwd = userRepository.GetRepoPwd(postLoginReq.getEmail());
        String repoId = userRepository.GetRepoId(postLoginReq.getEmail());
        String pwd;
        try{
            pwd = Decrypt.decryptAES256(repoPwd);
        } catch (Exception e) {
            throw new BaseException(FAIL_DECRYPT_PWD);
        }

        if(postLoginReq.getPassword().equals(pwd)){
            String jwt = jwtService.createJwt(repoId);
            return new PostLoginRes(jwt);
        }
        else {
            throw new BaseException(FAIL_TO_LOGIN);
        }
    }

    public void updateUserPwd(String id, String pwd) throws BaseException {
        try {
            String newPwd = Encrypt.encryptAES256(pwd);
            User user = userRepository.findOne(id);
            user.setPassword(newPwd);
            userRepository.save(user);
        }catch (Exception e) {
            throw new BaseException(FAIL_TO_CHANGE_PWD);
        }
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
        String provider = "K";
        User user = new User(id,email,nickname,name,phone,provider);
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
