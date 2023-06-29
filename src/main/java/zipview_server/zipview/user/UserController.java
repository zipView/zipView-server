package zipview_server.zipview.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import static zipview_server.config.BaseResponseStatus.*;

import zipview_server.zipview.security.MemberRepository;
import zipview_server.zipview.security.MemberService;
import zipview_server.zipview.security.dto.MemberResponseDto;

import zipview_server.zipview.user.dto.*;



@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/auth/exist")
    public BaseResponse<String> checkMember(@RequestParam String email){
        String result;

        if(memberRepository.existsByEmail(email)){
            result="회원";
        }else {
            result="비회원";
        }
        return new BaseResponse<>(result);
    }



    /**
     * 내 정보 수정하기(닉네임만 수정)
     */
    @PatchMapping("/my/info")
    public BaseResponse<String> changeInfo(@RequestBody PatchInfo patchInfo)  {
        try{
            MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
            String userId = myInfoBySecurity.getId();
            if(userId.isEmpty()){
                return new BaseResponse<>(EMPTY_JWT);
            }
            else{
                userService.updateNickname(userId, patchInfo);
                return new BaseResponse<>("정보 수정 성공");
            }
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 키워드 설정하기
     */
    @PatchMapping("/my/keyword")
    public BaseResponse<String> setKeyword(@RequestBody patchKeywordReq patchKeywordReq) throws BaseException {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        String userId = myInfoBySecurity.getId();
        log.info(userId);
        if(userId.isEmpty()){
            return new BaseResponse<>(EMPTY_JWT);
        }
        else{
            userService.setKeyword(userId,patchKeywordReq);
            return new BaseResponse<>("키워드 설정 성공");
        }

    }







}
