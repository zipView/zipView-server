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
    @Query("select r from Review r where r.rentMin >= :rentMin and r.rentMax <= :rentMax and " +
            "r.depositMin >= :depositMin and r.depositMax <= :depositMax and " +
            "r.maintenanceFeeMin >= :maintenanceFeeMin and r.maintenanceFeeMax <= :maintenanceFeeMax and " +
            "r.roomType = :roomType and r.floor = :floor and r.roomSize = :roomSize and r.roomStructure = :roomStructure and r.transactionType = :transactionType")
    List<Review> getReview(@Param("rentMin") int rentMin, @Param("rentMax") int rentMax, @Param("depositMin") int depositMin, @Param("depositMax") int depositMax, @Param("maintenanceFeeMin") int maintenanceFeeMin, @Param("maintenanceFeeMax") int maintenanceFeeMax,
                           @Param("roomType") RoomType roomType, @Param("floor") Floor floor, @Param("roomSize") RoomSize roomSize, @Param("roomStructure") RoomStructure roomStructure, @Param("transactionType") TransactionType transactionType);

//requestReviewFilterDto.getRentMin(), requestReviewFilterDto.getRentMax(), requestReviewFilterDto.getDepositMin(), requestReviewFilterDto.getDepositMax(), requestReviewFilterDto.getMaintenanceFeeMin(), requestReviewFilterDto.getMaintenanceFeeMax(), requestReviewFilterDto.getRoomType(), requestReviewFilterDto.getFloor(), requestReviewFilterDto.getRoomSize(), requestReviewFilterDto.getRoomStructure(), requestReviewFilterDto.getTransactionType())
//

}
