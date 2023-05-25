package zipview_server.dto.review;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WriteCommentRequest {
//
//    @NotNull(message = "게시글 id를 필수로 기재해주세요.")
//    private Long reviewId;
    //유저아이디

    @NotNull(message = "댓글 내용을 입력해주세요.")
    private String content;

    // 유저 빌더
   public WriteCommentRequestDto toWriteCommentRequestDto() {
       return WriteCommentRequestDto.builder()
            //   .reviewId(reviewId)
               .content(content)
               .build();
   }


}
