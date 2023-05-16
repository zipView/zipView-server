package zipview_server.zipview.feedback.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zipview_server.zipview.user.dto.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback extends BaseEntity {
    @Id
    private String fb_idx;
    private String user_idx;
    private String fb_type;
    private String fb_title;
    private String fb_content;
    private String fb_email;
    private String isDeleted;
    private String isCompleted;
}
