package kitchenpos.fixture;

import java.util.List;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.Order;
import kitchenpos.orders.common.domain.OrderLineItem;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.store.domain.tobe.OrderTable;

public class OrderFixture {

    public static Order createTakeOutRequest(OrderLineItem... orderLineItem) {
        Order order = new Order();
        order.setType(OrderType.TAKEOUT);
        order.setOrderLineItems(List.of(orderLineItem));

        return order;
    }

    public static Order createEatInRequest(final OrderTable orderTable,
            OrderLineItem... orderLineItem) {
        Order order = new Order();
        order.setType(OrderType.EAT_IN);
        if (orderTable != null) {
            order.setOrderTableId(orderTable.getId());
        }
        order.setOrderLineItems(List.of(orderLineItem));

        return order;
    }

    public static Order createDeliveryRequest(final String deliveryAddress,
            OrderLineItem... orderLineItem) {
        Order order = new Order();
        order.setType(OrderType.DELIVERY);
        if (deliveryAddress != null) {
            order.setDeliveryAddress(deliveryAddress);
        }
        order.setOrderLineItems(List.of(orderLineItem));

        return order;
    }

    public static OrderLineItem createOrderLineItem(Menu menu, Integer quantity) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setMenu(menu);
        orderLineItem.setQuantity(quantity);
        orderLineItem.setMenuId(menu.getId());
        orderLineItem.setPrice(menu.getPrice());
        return orderLineItem;
    }
}
