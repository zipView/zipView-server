package zipview_server.zipview.user.dto;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class MessageDto {
    private String to;

}
