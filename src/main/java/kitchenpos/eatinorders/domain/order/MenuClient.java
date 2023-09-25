package kitchenpos.eatinorders.domain.order;

import kitchenpos.common.domain.OrderLineItems;

public interface MenuClient {
    void validateOrderLineItems(final OrderLineItems orderLineItems);
}
