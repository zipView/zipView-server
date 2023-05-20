package zipview_server.dto.review;

import zipview_server.domain.ReviewImage;

public class ReviewImageDto {
    private String path;
    private int num;
    private boolean isHarm;

    public ReviewImageDto(ReviewImage reviewImage) {
        this.path = reviewImage.getPath();
        this.num = reviewImage.getNum();
        this.isHarm = reviewImage.isHarm();

    }
}
