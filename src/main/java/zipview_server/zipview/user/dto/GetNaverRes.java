package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetNaverRes {
    private String code;
    private String state;
    private String address;
}
