package kitchenpos.menu.infra.persistence;

import static kitchenpos.menu.domain.model.QMenuSummary.menuSummary;
import static kitchenpos.product.domain.model.QProductSummary.productSummary;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.domain.repository.MenuQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QuerydslMenuQueryRepository implements MenuQueryRepository {
    private final JPAQueryFactory queryFactory;

    public QuerydslMenuQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<MenuSummary> findAll() {
        return queryFactory
                .selectFrom(menuSummary)
                .distinct()
                .leftJoin(menuSummary.productSummaries, productSummary).fetchJoin()
                .fetch();
    }
}
