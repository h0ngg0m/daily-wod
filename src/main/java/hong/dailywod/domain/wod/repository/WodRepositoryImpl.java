package hong.dailywod.domain.wod.repository;

import jakarta.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import hong.dailywod.domain.wod.model.QWod;
import hong.dailywod.domain.wod.model.Wod;
import hong.dailywod.global.util.PagingUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WodRepositoryImpl implements WodRepositoryCustom {

    private final EntityManager eiEntityManager;
    private final JPAQueryFactory jpaQueryFactory;
    private static final QWod wod = QWod.wod;

    @Override
    public Wod persist(Wod wod) {
        eiEntityManager.persist(wod);
        return wod;
    }

    @Override
    public Page<Wod> findAllByPagination(Pageable pageable) {
        return new PageImpl<>(
                this.jpaQueryFactory
                        .selectFrom(wod)
                        .orderBy(PagingUtil.getOrderBy(pageable.getSort(), Wod.class, "wod"))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .stream()
                        .toList(),
                pageable,
                this.jpaQueryFactory.select(wod.id).from(wod).stream().count());
    }
}
