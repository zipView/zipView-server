package zipview_server.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import zipview_server.dto.review.ReviewDto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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


    public static Review createReview(int price, String content, String title, List<ReviewImage> reviewImage, int likeNum, RoomType roomType, Residence residence) {
        Review review = new Review();
        review.price = price;
        review.content = content;
        review.title = title;
        review.reviewImage = reviewImage;
        review.likeNum = likeNum;
        review.roomType = roomType;
        review.residence = residence;
        return review;
    }
}
