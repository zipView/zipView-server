package zipview_server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomType {
    OFFICETEL("Officetel"),
    ONEROOM("Oneroom"),
    TWOROOM("Tworoom"),
    THREEROOM("Threeroom"),
    URBANHOUSE("Urbanhouse")
    ;

    private String roomType;
}
