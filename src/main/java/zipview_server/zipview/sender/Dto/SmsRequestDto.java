package zipview_server.zipview.sender.Dto;


import lombok.*;
import zipview_server.zipview.sender.Dto.MessageDto;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public
class SmsRequestDto {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<MessageDto> messages;

}
