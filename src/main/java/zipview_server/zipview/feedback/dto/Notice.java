package zipview_server.zipview.feedback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noIdx;
    private String title;
    private String content;



    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String updatedAt;

    public Notice(int noIdx, String title, String content, String createdAt, String updatedAt) {
        this.noIdx = noIdx;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
