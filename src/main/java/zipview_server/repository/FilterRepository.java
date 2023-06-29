package zipview_server.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import zipview_server.domain.*;
import java.util.List;
import java.util.Optional;

public interface FilterRepository extends JpaRepository<Review, Long> {

    //querydsl refec
    @Query("select r from Review r where (r.rentMax >= :rentMax and r.rentMin <= :rentMin) or " +
            "(r.depositMax >= :depositMax and r.depositMin <= :depositMin) or " +
            "(r.maintenanceFeeMax >= :maintenanceFeeMax and r.maintenanceFeeMin <= :maintenanceFeeMin) or " +
            "r.roomType = :roomType or r.floor = :floor or r.roomSize = :roomSize or r.roomStructure = :roomStructure or r.transactionType = :transactionType")
    List<Review> getReview(@Param("rentMin") int rentMin, @Param("rentMax") int rentMax, @Param("depositMin") int depositMin, @Param("depositMax") int depositMax, @Param("maintenanceFeeMin") int maintenanceFeeMin, @Param("maintenanceFeeMax") int maintenanceFeeMax,
                           @Param("roomType") RoomType roomType, @Param("floor") Floor floor, @Param("roomSize") RoomSize roomSize, @Param("roomStructure") RoomStructure roomStructure, @Param("transactionType") TransactionType transactionType);

//requestReviewFilterDto.getRentMin(), requestReviewFilterDto.getRentMax(), requestReviewFilterDto.getDepositMin(), requestReviewFilterDto.getDepositMax(), requestReviewFilterDto.getMaintenanceFeeMin(), requestReviewFilterDto.getMaintenanceFeeMax(), requestReviewFilterDto.getRoomType(), requestReviewFilterDto.getFloor(), requestReviewFilterDto.getRoomSize(), requestReviewFilterDto.getRoomStructure(), requestReviewFilterDto.getTransactionType())
//

}
