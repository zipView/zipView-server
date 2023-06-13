package zipview_server.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zipview_server.domain.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestReviewFilterDto {

    private Long id;
    private String userId;
    private String userEmail;
    private int rentMin;
    private int rentMax;
    private int depositMin;
    private int depositMax;
    private int maintenanceFeeMin;
    private int maintenanceFeeMax;
    private RoomType roomType;
    private Floor floor;
    private RoomSize roomSize;
    private RoomStructure roomStructure;
    private TransactionType transactionType;

    private RequestReviewFilterDto(Long id) {
        this.id = id;

    }

    public static RequestReviewFilterDto of(Long reviewId) {
        return new RequestReviewFilterDto(reviewId);
    }
}
