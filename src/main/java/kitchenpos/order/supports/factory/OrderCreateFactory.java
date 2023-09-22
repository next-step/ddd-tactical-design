package kitchenpos.order.supports.factory;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.eatinorders.domain.vo.OrderLineItems;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderCreateFactory {
    public static Order deliveryOrder(OrderLineItems orderLineItems, String deliveryAddress) {
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, deliveryAddress, null, null);
    }

    public static Order takeOutOrder(OrderLineItems orderLineItems) {
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, null, null, null);
    }

    public static Order eatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, null, orderTable, orderTable.getId());
    }
}
