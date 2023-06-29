package zipview_server.zipview.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatchMemberRes {
    private String id;
    private String result;
    public static PatchMemberRes delete(Member member){
        return PatchMemberRes.builder()
                .id(member.getId())
                .result("회원 삭제 완료")
                .build();
    }
}
