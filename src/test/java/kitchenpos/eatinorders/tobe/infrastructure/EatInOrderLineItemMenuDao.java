package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemMenu;

import java.util.List;

public interface EatInOrderLineItemMenuDao {
    void saveAll(List<EatInOrderLineItemMenu> eatInOrderLineItemMenus);
}
