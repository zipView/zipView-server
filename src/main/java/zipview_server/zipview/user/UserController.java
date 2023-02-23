package zipview_server.zipview.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import static zipview_server.config.BaseResponseStatus.*;

import zipview_server.utils.EmailRegex;
import zipview_server.utils.PwdRegex;
import zipview_server.zipview.user.dto.*;


import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${naver-login.secretkey}")
    private String secretKey;
    @Value("${naver-login.clientId}")
    private String clientId;

    @PostMapping("/new")
    public BaseResponse<CreateUserRes> saveUser(@RequestBody @Valid CreateUserReq createUserReq)  {
        try {
            User user = new User();
            String uuid = UUID.randomUUID().toString();
            user.setId(uuid);

            //비밀번호 형식 validation
            if(!PwdRegex.isRegexPwd(createUserReq.getPassword())){
               return new BaseResponse<>(INVALID_PWD);
            }
            //이메일 형식 validation
            if(!EmailRegex.isRegexEmail(createUserReq.getEmail())){
                return new BaseResponse<>(INVALID_EMAIL);
            }

            user.setEmail(createUserReq.getEmail());
            user.setName(createUserReq.getName());
            user.setNickname(createUserReq.getNickname());
            user.setPhone(createUserReq.getPhone());

            user.setPassword(createUserReq.getPassword());
            String id = userService.join(user);
            return new BaseResponse<>(new CreateUserRes(id));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @PostMapping("/findId")
    public BaseResponse<PostUserIdRes> findEmail(@RequestBody PostUserIdReq postUserIdReq) {
        try {
            String email = userRepository.GetUserEmail(postUserIdReq);

            return new BaseResponse<>(new PostUserIdRes(email));
        }catch (NoResultException e) {
            return new BaseResponse<>(NON_EXIST_MEMBER);
        }
    }

    @GetMapping("/checkMember")
    public BaseResponse<String> checkMember(@RequestParam String email){
        String result;
        if(userRepository.findByEmail(email).isEmpty()){
            result="비회원";
        }else {
            result="회원";
        }
        return new BaseResponse<>(result);
    }


    @GetMapping("/naver")
    public String naverConnect() {
        // state용 난수 생성
        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        // redirect
        StringBuffer url = new StringBuffer();
        url.append("https://nid.naver.com/oauth2.0/authorize?");
        url.append("client_id=" + clientId);
        url.append("&response_type=code");
        url.append("&redirect_uri=http://127.0.0.1:8080/navercallback");
        url.append("&state=" + state);

        String result = url.toString();
        return result;
    }

    @GetMapping("/navercallback")
    public BaseResponse<PostUserIdRes> naverCallback(@RequestParam String code, @RequestParam String state) throws BaseException {
        User socialUser = userService.socialLogin(code, state);
        String id =userService.socialJoin(socialUser);
        return new BaseResponse<>(new PostUserIdRes(id));
    }










}
