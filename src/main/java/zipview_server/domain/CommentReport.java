package zipview_server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport {


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public void setComment(zipview_server.domain.Comment comment) {
        this.comment = comment;
    }

    // user 연결

    public static CommentReport createCommentReport (Comment comment) {
        CommentReport commentReport = new CommentReport();
        commentReport.comment = comment;
        //user 연결
        return commentReport;
    }

}
