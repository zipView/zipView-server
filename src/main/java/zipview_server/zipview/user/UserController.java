package zipview_server.zipview.user;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import static zipview_server.config.BaseResponseStatus.*;

import zipview_server.utils.Decrypt;
import zipview_server.utils.EmailRegex;
import zipview_server.utils.Encrypt;
import zipview_server.utils.PwdRegex;
import zipview_server.zipview.user.dto.*;


import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;




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
            user.setProvider("E");

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

    /**
     * 로그인
     */
    @PostMapping("/login")
    public BaseResponse<PostLoginRes>login(@RequestBody PostLoginReq postLoginReq) {
      try {
          PostLoginRes postLoginRes = userService.login(postLoginReq);
          return new BaseResponse<>(postLoginRes);
      } catch (BaseException e) {
          return new BaseResponse<>(e.getStatus());
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
    }

    /**
     * 비밀번호 바꾸기
     */
    @PatchMapping("/changepwd")
    public BaseResponse<String> changePwd(@RequestParam("id") String id,@RequestBody PatchPwdReq patchPwdReq) throws Exception {
        try {
            String userId = jwtService.getUserId();
            if(jwtService.getJwt().isEmpty()){
                return new BaseResponse<>(EMPTY_JWT);
            }
            String token = jwtService.getJwt();

            if (jwtService.validateToken(token) && userId.equals(id)) {
                String repoPwd = Decrypt.decryptAES256(userRepository.GetRepoPwdById(id));
                if (patchPwdReq.getCurrentPwd().equals(repoPwd)) {
                    if(!PwdRegex.isRegexPwd(patchPwdReq.getNewPwd())){
                        return new BaseResponse<>(INVALID_PWD);
                    }
                    userService.updateUserPwd(id, patchPwdReq.getNewPwd());
                } else {
                    return new BaseResponse<>(NOT_MATCH_USER_PWD);
                }
            } else {
                return new BaseResponse<>(INVALID_JWT);
            }
            return new BaseResponse<>("비밀번호를 변경하였습니다.");
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }


    }










}
