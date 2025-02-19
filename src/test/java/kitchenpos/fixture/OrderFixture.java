package kitchenpos.fixture;


import kitchenpos.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorder.domain.OrderLineItem;
import kitchenpos.eatinorder.domain.OrderStatus;
import kitchenpos.eatinorder.domain.OrderType;
import kitchenpos.menu.domain.Menu;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderFixture {
    public static EatInOrder order(OrderType type, OrderStatus status, List<OrderLineItem> orderLineItems) {
        EatInOrder order = new EatInOrder();
        order.setType(type);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        return order;
    }

    public static EatInOrder acceptedTakeoutOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.TAKEOUT, OrderStatus.ACCEPTED, orderLineItems);
    }

    public static EatInOrder waitingTakeoutOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.TAKEOUT, OrderStatus.WAITING, orderLineItems);
    }

    public static EatInOrder servedDeliveryOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.DELIVERY, OrderStatus.SERVED, orderLineItems);
    }

    public static EatInOrder deliveringDeliveryOrder(List<OrderLineItem> orderLineItems) {
        return order(OrderType.DELIVERY, OrderStatus.DELIVERING, orderLineItems);
    }

    public static EatInOrder deliveryOrder(String address, List<OrderLineItem> orderLineItems) {
        EatInOrder order = order(OrderType.DELIVERY, OrderStatus.WAITING, orderLineItems);
        order.setDeliveryAddress(address);
        return order;
    }

    public static EatInOrder eatInOrder(UUID orderTableId, List<OrderLineItem> orderLineItems) {
        EatInOrder order = order(OrderType.EAT_IN, OrderStatus.WAITING, orderLineItems);
        order.setOrderTableId(orderTableId);
        return order;
    }

    public static EatInOrder takeoutOrder(List<OrderLineItem> orderLineItems) {
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
