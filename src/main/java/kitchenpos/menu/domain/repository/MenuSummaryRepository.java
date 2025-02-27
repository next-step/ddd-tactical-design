package kitchenpos.menu.domain.repository;

import kitchenpos.menu.domain.model.MenuSummary;

public interface MenuSummaryRepository {
    MenuSummary save(MenuSummary menuSummary);
}
