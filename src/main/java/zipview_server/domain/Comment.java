package zipview_server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {


    @Id @GeneratedValue
    @Column(name ="comment_id")
    private Long id;

    // User !!

//    @NotNull
//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
//    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @NotNull
    private String content;

    private Comment(Review review, Long id,  String content) {
        this.id = id;
        this.review = review;
        this.content = content;
    }

    public static Comment createComment(Review review, Long id, String content) {
        Comment comment = new Comment();
        comment.id = id;
        comment.review = review;
        comment.content = content;
        return comment;
    }


    public static Comment of(Review review, Long id, String content) {
        return new Comment(review, id, content);
    }

    public void fixComment(String content) {
        this.content = content;
    }





}
