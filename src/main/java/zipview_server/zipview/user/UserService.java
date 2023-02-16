package zipview_server.zipview.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.config.BaseException;
import zipview_server.utils.PwdRegex;


import java.util.List;

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
        userRepository.save(user);
        return user.getId();
    }
    private void validateDuplicateMember(User user) throws BaseException {
        List<User> findUser = userRepository.findByEmail(user.getEmail());
        if(!findUser.isEmpty()) {
            throw new BaseException(EXIST_EMAIL);
        }
    }
}
