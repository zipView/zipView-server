package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchPwdReq {
    private String currentPwd;
    private String newPwd;
}
