package zipview_server.zipview.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import zipview_server.zipview.security.dto.*;
import zipview_server.zipview.security.dto.PostUserIdRes;

import javax.persistence.NoResultException;

import java.util.Optional;

import static zipview_server.config.BaseResponseStatus.NON_EXIST_MEMBER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getNickname());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }
    @PatchMapping("/withdrawal")
    public ResponseEntity<PatchMemberRes> withdrawMember(){
        return ResponseEntity.ok(memberService.withdrawMember());
    }

    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getEmail(), request.getNickname()));
    }

    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getEmail(), request.getExPassword(), request.getNewPassword()));
    }








}