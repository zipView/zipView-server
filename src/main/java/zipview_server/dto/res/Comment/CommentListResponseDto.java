package zipview_server.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.domain.Comment;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {

    //offset

    private List<CommentListDto> comments;

    public CommentListResponseDto(List<CommentListDto> comments) {
        this.comments = comments;
    }

    public static CommentListResponseDto of(List<CommentListDto> comments) {
        return new CommentListResponseDto(comments);
    }

/*
    @Getter
    public static class CommentList {
        //유저 아이디, 유저 프사,
        private Long reviewId;
        private String content;
        private String writtenDate;
       // 작성자인가?
        private Boolean isAuthor;
        private void setWrittenDate(Comment comment){
            DayOfWeek dayOfWeek = comment.getCreatedAt().getDayOfWeek();
            String day = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);
            String date = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            this.writtenDate = String.format("%s (%s)", date, day);
        }

        public CommentList(Comment comment) {
            this.reviewId = comment.getId();
            this.content = comment.getContent();
            setWrittenDate(comment);
        }


    }*/
}
