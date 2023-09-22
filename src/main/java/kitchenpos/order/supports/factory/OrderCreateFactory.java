package kitchenpos.order.supports.factory;

import kitchenpos.order.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Component
public class OrderCreateFactory {

    public static Order deliveryOrder(OrderLineItems orderLineItems, String deliveryAddress) {
        if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return new Order(UUID.randomUUID(), OrderType.DELIVERY, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, deliveryAddress, null, null);
    }

    public static Order takeOutOrder(OrderLineItems orderLineItems) {
        return new Order(UUID.randomUUID(), OrderType.TAKEOUT, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, null, null, null);
    }

    public static Order eatInOrder(OrderLineItems orderLineItems, OrderTable orderTable) {
        return new Order(UUID.randomUUID(), OrderType.EAT_IN, OrderStatus.WAITING, LocalDateTime.now(), orderLineItems, null, orderTable, orderTable.getId());
    }
}
