package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;

import java.util.List;

public interface EatInOrderLineItemDao {
    void saveAll(List<EatInOrderLineItem> eatInOrderLineItems);
}
