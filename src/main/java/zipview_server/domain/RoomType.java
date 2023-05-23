package zipview_server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoomType {
    OFFICETEL("Officetel"),
    STUDIO("Studio"),
    APARTMENT("Apartment");

    private String roomType;
}
