package hong.dailywod.domain.wodhistory.repository;

import hong.dailywod.domain.wodhistory.model.WodHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WodHistoryRepository
        extends JpaRepository<WodHistory, Long>, WodHistoryRepositoryCustom {

    @Query("SELECT wh FROM WodHistory wh JOIN wh.wod w WHERE w.wodDate BETWEEN :startDate AND :endDate AND wh.user.id = :userId")
    List<WodHistory> findAllByWodDateBetweenAndUserId(@Param("startDate") LocalDate startDate,
                                                   @Param("endDate") LocalDate endDate,
                                                   @Param("userId") Long userId);
}
