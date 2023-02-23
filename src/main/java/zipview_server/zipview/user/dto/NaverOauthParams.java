package zipview_server.zipview.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NaverOauthParams {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Long expires_in;


}
