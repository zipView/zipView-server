package zipview_server.dto.review;

import lombok.Data;
import zipview_server.domain.ReviewImage;

@Data
public class ReviewImageDto {

    private String name;
    private Long fileSize;
    private String path;
    private boolean isHarm;

    public ReviewImageDto(ReviewImage reviewImage) {
        name = reviewImage.getName();
        fileSize = reviewImage.getFileSize();
        path = reviewImage.getPath();
        isHarm = reviewImage.isHarm();
    }
}
