package zipview_server.zipview.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.config.BaseException;
import zipview_server.zipview.security.dto.Member;
import zipview_server.zipview.user.dto.*;


import java.util.List;

import static zipview_server.config.BaseResponseStatus.*;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;


    @Value("${naver-login.secretkey}")
    private String secretKey;
    @Value("${naver-login.clientId}")
    private String clientId;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateNickname(String id,PatchInfo patchInfo) throws BaseException {
        try {
            if(patchInfo.getNickname().isEmpty()){
                throw new BaseException(EMPTY_VALUE);
            }
            Member user = userRepository.findOne(id);
            user.setNickname(patchInfo.getNickname());
            userRepository.save(user);
        }catch (Exception e){
            throw new BaseException(FAIL_TO_CHANGE_PWD);
        }
    }



    @Transactional
    public void setKeyword(String id, patchKeywordReq patchKeywordReq) throws BaseException {
        try{
            Member user = userRepository.findOne(id);
            if(patchKeywordReq.getKeyword1()==null && patchKeywordReq.getKeyword2()==null & patchKeywordReq.getKeyword3()==null) {
                throw new BaseException(EMPTY_KEYWORD);
            }
            user.setKeyword(patchKeywordReq.getKeyword1(), patchKeywordReq.getKeyword2(), patchKeywordReq.getKeyword3());
            userRepository.save(user);
        }catch (Exception e) {
            throw new BaseException(FAIL_TO_SET_KEYWORD);
        }
    }




    private void validateDuplicateMember(Member user) throws BaseException {
        List<Member> findUser = userRepository.findByEmail(user.getEmail());
        if(!findUser.isEmpty()) {
            throw new BaseException(EXIST_EMAIL);
        }
    }
    private void validateExistNickname(Member user) throws BaseException {
        List<Member> findUser = userRepository.findByNickName(user.getNickname());
        if(!findUser.isEmpty()){
            throw new BaseException(EXIST_NICKNAME);
        }
    }


}
