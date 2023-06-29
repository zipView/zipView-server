package zipview_server.zipview.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zipview_server.zipview.user.dto.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor

public class Feedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fb_idx;
    private String user_idx;
    private String fb_type;
    private String fb_title;
    private String fb_content;
    private String fb_email;
    private String isDeleted;
    private String isCompleted;

    public void setFb(String user_idx,String fb_type,String fb_title, String fb_content, String fb_email) {
        this.user_idx = user_idx;
        this.fb_type = fb_type;
        this.fb_title = fb_title;
        this.fb_content = fb_content;
        this.fb_email = fb_email;
    }
}
