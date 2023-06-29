package zipview_server.dto.req.Review;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.domain.*;
import zipview_server.dto.req.Comment.WriteCommentRequestDto;

import java.util.ArrayList;
import java.util.List;
@Data
public class WriteReviewRequest {
    //   private Long id;
    private String userId;
    private String userEmail;
    private List<MultipartFile> image = new ArrayList<>();
    private int rentMin;
    private int rentMax;
    private int depositMin;
    private int depositMax;
    private int maintenanceFeeMin;
    private int maintenanceFeeMax;
    private String content;
    private String title;
    private int heart;
    private RoomType roomType;
    private Residence residence;
    private Floor floor;
    private RoomSize roomSize;
    private RoomStructure roomStructure;
    private TransactionType transactionType;
    private int report;
/*


        review.rentMin = rentMin;
        review.rentMax = rentMax;
        review.depositMin = depositMin;
        review.depositMax = depositMax;
        review.maintenanceFeeMin = maintenanceFeeMin;
        review.maintenanceFeeMax = maintenanceFeeMax;
        review.title = title;
        review.content = content;
        review.heart = heart;
        review.roomType = roomType;
        review.residence = residence;
        review.report = report;
        review.floor = floor;
        review.roomSize = roomSize;
        review.roomStructure = roomStructure;
        review.transactionType = transactionType;

    public RequestReviewDto toRequestReviewDto(List<String> images) {
        return RequestReviewDto.of(price, content, title, images, like, roomType, residence);
    }*/

    /*public WriteReviewRequestDto toWriteReviewRequestDto() {
        return WriteReviewRequestDto.builder()
                .content(content)
                .depositMax(depositMax)
                .depositMin(depositMin)
                .floor(floor)
                .like(like)
                .maintenanceFeeMax(maintenanceFeeMax)
                .maintenanceFeeMin(maintenanceFeeMin)
                .rentMax(rentMax)
                .rentMin(rentMin)
                .residence(residence)
                .roomSize(roomSize)
                .roomStructure(roomStructure)
                .roomType(roomType)
                .title(title)
                .report(report)
                .transactionType(transactionType)
                .build();
    }*/
}