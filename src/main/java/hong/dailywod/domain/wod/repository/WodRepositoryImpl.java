package hong.dailywod.domain.wod.repository;

import jakarta.persistence.EntityManager;

import hong.dailywod.domain.wod.model.Wod;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WodRepositoryImpl implements WodRepositoryCustom {

    private final EntityManager eiEntityManager;

    @Override
    public Wod persist(Wod wod) {
        eiEntityManager.persist(wod);
        return wod;
    }
}
