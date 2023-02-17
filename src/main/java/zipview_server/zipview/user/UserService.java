package zipview_server.zipview.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.config.BaseException;
import zipview_server.utils.Encrypt;
import zipview_server.utils.PwdRegex;


import java.util.List;

import static org.apache.tomcat.util.net.openssl.ciphers.Encryption.AES128;
import static zipview_server.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
public class UserService {
    final UserRepository userRepository;

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
