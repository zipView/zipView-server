package zipview_server.dto.review;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WriteCommentRequest {
//
//    @NotNull(message = "게시글 id를 필수로 기재해주세요.")
//    private Long reviewId;
    //유저아이디

    private Long id;
    @NotNull(message = "댓글 내용을 입력해주세요.")
    private String content;
    private int report;

    // 유저 빌더
   public WriteCommentRequestDto toWriteCommentRequestDto() {
       return WriteCommentRequestDto.builder()
            //   .reviewId(reviewId)
               .content(content)
               .report(report)
               .build();
   }


}
