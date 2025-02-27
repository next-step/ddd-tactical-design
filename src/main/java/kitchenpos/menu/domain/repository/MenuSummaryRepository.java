package kitchenpos.menu.domain.repository;

import kitchenpos.menu.domain.model.MenuSummary;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuSummaryRepository {
    MenuSummary save(MenuSummary menuSummary);
}
