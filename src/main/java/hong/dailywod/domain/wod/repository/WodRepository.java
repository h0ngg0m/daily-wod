package hong.dailywod.domain.wod.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.domain.wod.model.WodType;

@Repository
public interface WodRepository extends JpaRepository<Wod, Long>, WodRepositoryCustom {

    Optional<Wod> findByWodDateAndType(LocalDate wodDate, WodType type);

    List<Wod> findAllByWodDateBetween(LocalDate startDate, LocalDate endDate);
}
