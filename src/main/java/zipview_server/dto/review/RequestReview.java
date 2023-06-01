package zipview_server.dto.review;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.domain.*;

import java.util.ArrayList;
import java.util.List;
@Data
public class RequestReview {
 //   private Long id;
    private List<MultipartFile> image = new ArrayList<>();
   private int rentMin;
   private int rentMax;
   private int depositMin;
   private int depositMax;
   private int maintenanceFeeMin;
   private int maintenanceFeeMax;
    private String content;
    private String title;
    private int likenum;
    private RoomType roomType;
    private Residence residence;
    private Floor floor;
    private RoomSize roomSize;
    private RoomStructure roomStructure;
    private TransactionType transactionType;
    private int report;
/*
    public RequestReviewDto toRequestReviewDto(List<String> images) {
        return RequestReviewDto.of(price, content, title, images, likenum, roomType, residence);
    }*/
}