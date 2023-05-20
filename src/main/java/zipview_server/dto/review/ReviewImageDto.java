package zipview_server.dto;

import zipview_server.domain.ReviewImage;

public class ReviewImageDto {
    private String path;
    private int num;
    private boolean isHarm;

    public ReviewImageDto(ReviewImage reviewImage) {
        path = reviewImage.getPath();
        num = reviewImage.getNum();
        isHarm = reviewImage.isHarm();

    }
}
