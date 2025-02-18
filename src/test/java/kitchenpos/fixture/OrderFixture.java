package kitchenpos.fixture;

import kitchenpos.deliveryorder.domain.DeliveryOrder;
import kitchenpos.deliveryorder.domain.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderFixture {
    public static DeliveryOrder order(OrderType type, OrderStatus status, List<OrderLineItem> orderLineItems) {
        DeliveryOrder order = new DeliveryOrder();
        order.setType(type);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    public static DeliveryOrder acceptedTakeoutOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.TAKEOUT, OrderStatus.ACCEPTED, orderLineItems);
    }

    public static DeliveryOrder waitingTakeoutOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.TAKEOUT, OrderStatus.WAITING, orderLineItems);
    }

    public static DeliveryOrder servedDeliveryOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.DELIVERY, OrderStatus.SERVED, orderLineItems);
    }

    public static DeliveryOrder deliveringDeliveryOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.DELIVERY, OrderStatus.DELIVERING, orderLineItems);
    }

    public static DeliveryOrder deliveryOrder(String address, List<OrderLineItem> orderLineItems) {
        DeliveryOrder order = order(OrderType.DELIVERY, OrderStatus.WAITING, orderLineItems);
        order.setDeliveryAddress(address);
        return order;
    }

    public static DeliveryOrder eatInOrder(UUID orderTableId, List<OrderLineItem> orderLineItems) {
        DeliveryOrder order = order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems);
        order.setOrderTableId(orderTableId);
        return order;
    }

    public static DeliveryOrder takeoutOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.TAKEOUT, OrderStatus.WAITING, orderLineItems);
    }

    public static OrderLineItem orderLineItem(Menu menu, long quantity) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setMenuId(menu.getId());
        orderLineItem.setMenu(menu);
        orderLineItem.setQuantity(quantity);
        orderLineItem.setPrice(menu.getPrice());
        return orderLineItem;
    }

    public static OrderLineItem orderLineItem(UUID menuId, long quantity, BigDecimal price) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setMenuId(menuId);
        orderLineItem.setQuantity(quantity);
        orderLineItem.setPrice(price);
        return orderLineItem;
    }
}
