package hong.dailywod.domain.wodhistory.repository;

import jakarta.persistence.EntityManager;

import hong.dailywod.domain.wodhistory.model.WodHistory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WodHistoryRepositoryImpl implements WodHistoryRepositoryCustom {

    private final EntityManager eiEntityManager;

    @Override
    public WodHistory persist(WodHistory wodHistory) {
        eiEntityManager.persist(wodHistory);
        return wodHistory;
    }
}
