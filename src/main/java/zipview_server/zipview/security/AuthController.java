package zipview_server.zipview.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zipview_server.zipview.security.dto.*;
import zipview_server.zipview.user.UserRepository;

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



    @PostMapping("/findId")
    public ResponseEntity<PostUserIdRes> findEmail(@RequestBody PostUserIdReq postUserIdReq) {
        String email = userRepository.GetUserEmail(postUserIdReq);
        return ResponseEntity.ok(new PostUserIdRes(email));
    }


}
