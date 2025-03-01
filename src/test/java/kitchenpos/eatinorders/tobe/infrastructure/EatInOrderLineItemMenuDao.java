package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.poc.EatInOrderLineItemMenu;

import java.util.List;

@Deprecated
public interface EatInOrderLineItemMenuDao {
    void saveAll(List<EatInOrderLineItemMenu> eatInOrderLineItemMenus);
}
