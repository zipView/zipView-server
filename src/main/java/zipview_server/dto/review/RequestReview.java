package zipview_server.dto.review;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.domain.Residence;
import zipview_server.domain.RoomType;

import java.util.ArrayList;
import java.util.List;
@Data
public class RequestReview {
 //   private Long id;
    private List<MultipartFile> image = new ArrayList<>();
    private int price;
    private String content;
    private String title;
    private int likenum;
    private RoomType roomType;
    private Residence residence;
/*
    public RequestReviewDto toRequestReviewDto(List<String> images) {
        return RequestReviewDto.of(price, content, title, images, likenum, roomType, residence);
    }*/
}