package zipview_server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zipview_server.domain.Residence;
import zipview_server.domain.ReviewImage;
import zipview_server.domain.RoomType;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestReviewDto {

    private int price;
    private String content;
    private String title;
    private List<ReviewImage> reviewImage = new ArrayList<>();
    private
    private int likeNum;
    private RoomType roomType;
    private Residence residence;

}
