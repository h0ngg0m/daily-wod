package hong.dailywod.domain.wodhistory.repository;

import hong.dailywod.domain.wodhistory.model.WodHistory;

public interface WodHistoryRepositoryCustom {

    WodHistory persist(WodHistory wodHistory);
}
