package zipview_server.zipview.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zipview_server.config.BaseResponse;
import zipview_server.zipview.security.dto.*;

import javax.persistence.NoResultException;

import static zipview_server.config.BaseResponseStatus.NON_EXIST_MEMBER;

@Slf4j
@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    //수정 필요
    @GetMapping("/exist")
    public ResponseEntity<String> checkMember(@RequestParam String email){
        String result;

        if(memberRepository.existsByEmail(email)){
            result="회원";
        }else {
            result="비회원";
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/findId")
    public ResponseEntity<PostUserIdRes> findEmail(@RequestBody PostUserIdReq postUserIdReq) {
        String email = userRepository.GetUserEmail(postUserIdReq);
        return ResponseEntity.ok(new PostUserIdRes(email));
    }


}
