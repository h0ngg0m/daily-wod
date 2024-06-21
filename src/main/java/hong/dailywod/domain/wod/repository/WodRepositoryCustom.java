package hong.dailywod.domain.wod.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hong.dailywod.domain.wod.model.Wod;

public interface WodRepositoryCustom {

    Wod persist(Wod wod);

    Page<Wod> findAllByPagination(Pageable pageable);
}
