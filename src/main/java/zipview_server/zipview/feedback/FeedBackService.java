package zipview_server.zipview.feedback;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.config.BaseException;
import zipview_server.utils.Encrypt;
import zipview_server.zipview.feedback.dto.Feedback;
import zipview_server.zipview.user.dto.User;

import java.util.Objects;

import static zipview_server.config.BaseResponseStatus.FAIL_ENCRYPT_PWD;
import static zipview_server.config.BaseResponseStatus.FAIL_TO_SEND_FB;

@Service
@Transactional(readOnly = true)
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;

    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    @Transactional
    public String sendFeedback(Feedback feedback) throws BaseException{
        // 검사 조건 추가하기
        try{
            feedBackRepository.save(feedback);
            return feedback.getFb_idx();
        }catch (Exception ignored){
            throw new BaseException(FAIL_TO_SEND_FB);
        }
    }


}
