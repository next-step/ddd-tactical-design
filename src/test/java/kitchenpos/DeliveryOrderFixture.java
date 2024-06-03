package kitchenpos;

import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderStatus;
import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;
import kitchenpos.support.domain.OrderType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.orderLineItem;

public class DeliveryOrderFixture {

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status, final String deliveryAddress) {
        final DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.DELIVERY);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(List.of(orderLineItem()));
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status) {
        final DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.TAKEOUT);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(List.of(orderLineItem()));
        return order;
    }

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status, final OrderTable orderTable) {
        final DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(List.of(orderLineItem()));
        order.setOrderTable(orderTable);
        return order;
    }
}
