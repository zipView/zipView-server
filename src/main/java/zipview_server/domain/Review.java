package zipview_server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.dto.ReviewDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name ="review_id")
    private Long id;

  //  User 연결!!

    @NotNull
    private int price;

    @NotNull
    private String content;

    @NotNull
    private String title;

//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
//    private LocalDateTime createTime;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImage = new ArrayList<>();

    private int likeNum;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private Residence residence;

    public Review(int price, String content, int likeNum, ) {
        this.price = price;
        this.content = content;
    }

    public static Review createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.price = reviewDto.getPrice();
        review.content = reviewDto.getContent();
        review.title = reviewDto.getTitle();
        review.reviewImage = review.getReviewImage();
        review.likeNum = review.getLikeNum();
        review.roomType = review.getRoomType();
        review.residence = review.getResidence();
    }
}
