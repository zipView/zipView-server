package zipview_server.zipview.feedback;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zipview_server.config.BaseException;
import zipview_server.config.BaseResponse;
import zipview_server.zipview.feedback.dto.CreateFeedBackReq;
import zipview_server.zipview.feedback.dto.Feedback;
import zipview_server.zipview.user.dto.JwtService;
import java.util.UUID;

import static zipview_server.config.BaseResponseStatus.EMPTY_JWT;

@RequiredArgsConstructor
@RestController
public class FeedBackController {
    private final FeedBackService feedBackService;
    private final FeedBackRepository feedBackRepository;
    private final JwtService jwtService;

    @PostMapping("/feedback")
    public BaseResponse<String> saveUser(@RequestBody CreateFeedBackReq createFeedBackReq)  {
        try {
            Feedback feedBack = new Feedback();

            String uuid = UUID.randomUUID().toString();
            feedBack.setFb_idx(uuid);

            String userId = jwtService.getUserId();
            if(jwtService.getJwt().isEmpty()){
                return new BaseResponse<>(EMPTY_JWT);
            }
            String token = jwtService.getJwt();
            if (jwtService.validateToken(token) && userId.equals(createFeedBackReq.getUserId())) {
                feedBack.setFb_type(createFeedBackReq.getType());
                feedBack.setFb_title(createFeedBackReq.getTitle());
                feedBack.setFb_content(createFeedBackReq.getContent());
                feedBack.setFb_email(createFeedBackReq.getEmail());
            }
            String id = feedBackService.sendFeedback(feedBack);

            return new BaseResponse<>(id);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
    }
