package hong.dailywod.domain.wodhistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hong.dailywod.domain.wodhistory.model.WodHistory;

@Repository
public interface WodHistoryRepository
        extends JpaRepository<WodHistory, Long>, WodHistoryRepositoryCustom {}
