package kitchenpos.orders.tobe.domain.factory;

import static kitchenpos.orders.tobe.domain.OrderType.*;

import java.time.LocalDateTime;

import kitchenpos.orders.tobe.domain.Order;
import kitchenpos.orders.tobe.domain.OrderLineItems;
import kitchenpos.orders.tobe.domain.OrderStatus;
import kitchenpos.orders.tobe.domain.OrderTable;
import kitchenpos.orders.tobe.domain.OrderType;
import kitchenpos.orders.tobe.domain.TakeoutOrder;

public class TakeoutOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(OrderType orderType, OrderLineItems orderLineItems, OrderTable orderTable, String deliveryAddress) {
        if (orderType != TAKEOUT) {
            throw new IllegalArgumentException(WRONG_ORDER_TYPE_ERROR);
        }

        return new TakeoutOrder(
            OrderStatus.WAITING,
            LocalDateTime.now(),
            orderLineItems
        );
    }
}
