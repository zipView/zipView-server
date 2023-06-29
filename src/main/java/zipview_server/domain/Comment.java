package zipview_server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentReport> commentList = new ArrayList<>();

    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    @NotNull
    private String content;

    @NotNull
    private Integer report;


    private Comment(Review review, Long id,  String content, LocalDateTime createTime, int report) {
        this.id = id;
        this.review = review;
        this.content = content;
        this.createTime = createTime;
        this.report = report;
    }

    public static Comment createComment(Review review, Long id, String content, LocalDateTime createTime, int report) {
        Comment comment = new Comment();
        comment.id = id;
        comment.review = review;
        comment.createTime = createTime;
        comment.content = content;
        comment.report = report;
        return comment;
    }

    public void increaseReport() {
        this.report +=1 ;
    }


    public static Comment of(Review review, Long id, String content, LocalDateTime createTime, int report) {
        return new Comment(review, id, content, createTime, report);
    }

    public void fixComment(String content) {
        this.content = content;
    }





}
