package kitchenpos.order.takeout.domain.service;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.takeout.domain.model.TakeOutOrder;
import org.springframework.stereotype.Service;

@Service
public class DefaultTakeoutService implements TakeoutService {

    @Override
    public TakeOutOrder createTakeoutOrder(OrderId orderId, OrderLineItems orderLineItems) {
        return TakeOutOrder.createTakeoutOrder(orderId, orderLineItems);
    }
}
