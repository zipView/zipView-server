package zipview_server.zipview.feedback;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipview_server.config.BaseException;
import zipview_server.utils.Encrypt;
import zipview_server.zipview.feedback.dto.CreateFeedBackReq;
import zipview_server.zipview.feedback.dto.CreateNoticeReq;
import zipview_server.zipview.feedback.dto.Feedback;
import zipview_server.zipview.feedback.dto.Notice;


import java.util.Objects;

import static zipview_server.config.BaseResponseStatus.*;

@Service
@Transactional(readOnly = true)
public class FeedBackService {

    private final FeedBackRepository feedBackRepository;

    public FeedBackService(FeedBackRepository feedBackRepository) {
        this.feedBackRepository = feedBackRepository;
    }

    @Transactional
    public String sendFeedback(String id, CreateFeedBackReq fbReq) throws BaseException{
        try{
            Feedback fb =  new Feedback();
            if(fbReq.getTitle().isEmpty()||fbReq.getContent().isEmpty()||fbReq.getEmail().isEmpty()){
                throw new BaseException(EMPTY_VALUE);
            }
            fb.setFb(id, fbReq.getType(),fbReq.getTitle(),fbReq.getContent(),fbReq.getEmail());
            feedBackRepository.save(fb);
            return fb.getFb_idx().toString();
        }catch (Exception ignored){
            throw new BaseException(FAIL_TO_SEND_FB);
        }
    }

    @Transactional
    public String sendNotice(String id, CreateNoticeReq noticeReq) throws BaseException {
        try{
            if(noticeReq.getTitle().isEmpty()||noticeReq.getContent().isEmpty()){
                throw new BaseException(EMPTY_VALUE);
            }
            Notice notice = new Notice();
            notice.setUserId(id);
            notice.setTitle(noticeReq.getTitle());
            notice.setContent(noticeReq.getContent());
            feedBackRepository.saveNotice(notice);
            return notice.getNoIdx().toString();
        }catch (Exception e){
            throw new BaseException(FAIL_TO_SEND_NOTICE);
        }

    }


}
