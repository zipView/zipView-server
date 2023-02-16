package zipview_server.zipview.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import static zipview_server.config.BaseResponseStatus.*;

import zipview_server.utils.PwdRegex;
import zipview_server.zipview.user.dto.CreateUserReq;
import zipview_server.zipview.user.dto.CreateUserRes;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/new")
    public BaseResponse<CreateUserRes> saveUser(@RequestBody @Valid CreateUserReq createUserReq) {
        try {
            User user = new User();
            String uuid = UUID.randomUUID().toString();

            user.setId(uuid);

            //비밀번호 형식 validation
            if(!PwdRegex.isRegexPwd(createUserReq.getPassword())){
               return new BaseResponse<>(INVALID_PWD);
            }
            user.setEmail(createUserReq.getEmail());
            user.setNickname(createUserReq.getNickname());
            user.setPhone(createUserReq.getPhone());
            user.setPassword(createUserReq.getPassword());

            String id = userService.join(user);
            return new BaseResponse<>(new CreateUserRes(id));
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }




}
