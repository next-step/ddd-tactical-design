package kitchenpos.order.takeout.domain.service;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.takeout.domain.model.TakeOutOrder;

public interface TakeoutService {
    TakeOutOrder createTakeoutOrder(OrderId orderId, OrderLineItems orderLineItems);
}
