package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchInfo {
    private String nickname;

    public PatchInfo() {
        nickname=getNickname();
    }
}

