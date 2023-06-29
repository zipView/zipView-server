package zipview_server.zipview.feedback;

import org.springframework.web.bind.annotation.*;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import zipview_server.zipview.feedback.dto.*;
import zipview_server.zipview.security.MemberService;
import zipview_server.zipview.security.dto.MemberResponseDto;

import java.util.List;

import static zipview_server.config.BaseResponseStatus.*;


@RestController
public class FeedBackController {

    private final FeedBackService feedBackService;
    private final FeedBackRepository feedBackRepository;
    private final MemberService memberService;


    public FeedBackController(FeedBackService feedBackService, FeedBackRepository feedBackRepository, MemberService memberService) {
        this.feedBackService = feedBackService;
        this.feedBackRepository = feedBackRepository;
        this.memberService = memberService;
    }

    /**
     * 의견 보내기
     * @param createFeedBackReq
     * @return
     */
    @PostMapping("/notice/feedback")
    public BaseResponse<String> postFeedback(@RequestBody CreateFeedBackReq createFeedBackReq) {
        try {
            MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
            String userId = myInfoBySecurity.getId();

            if (userId.isEmpty()) {
                return new BaseResponse<>(EMPTY_JWT);
            }

            return new BaseResponse<>(feedBackService.sendFeedback(userId,createFeedBackReq));

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 공지사항 조회
     */
    @GetMapping("/notice")
    public BaseResponse<List<Notice>> noticeController() throws BaseException {
        List<Notice> notice = feedBackRepository.getNotice();
        return new BaseResponse<>(notice);
    }

    /**
     * 공지 작성
     * @param createNoticeReq
     * @return
     */
    @PostMapping("/admin/notice/new")
    public BaseResponse<String> PostNotice(@RequestBody CreateNoticeReq createNoticeReq) {
        try {
            Notice notice = new Notice();
            String title = createNoticeReq.getTitle();
            String content = createNoticeReq.getContent();
            if (title == null & content == null) {
                return new BaseResponse<>(EMPTY_VALUE);
            }
            notice.setTitle(title);
            notice.setContent(content);
            feedBackRepository.saveNotice(notice);

            return new BaseResponse<>("공지 작성 성공!");

        } catch (Exception e) {
            return new BaseResponse<>(e.toString());
        }
    }
}
