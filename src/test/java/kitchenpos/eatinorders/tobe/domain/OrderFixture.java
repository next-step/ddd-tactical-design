package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;

public class OrderFixture {

    public static Order order() {
        return new Order(occupiedOrderTable(), orderLineItems());
    }

    public static Order order(OrderTable orderTable) {
        return new Order(orderTable, orderLineItems());
    }

    public static Order order(OrderLineItems orderLineItems) {
        return new Order(occupiedOrderTable(), orderLineItems);
    }

    public static Order order(OrderTable orderTable, OrderLineItems orderLineItems) {
        return new Order(orderTable, orderLineItems);
    }

    public static Order acceptedOrder() {
        Order order = OrderFixture.order();
        order.accept();
        return order;
    }

    public static Order servedOrder() {
        Order order = acceptedOrder();
        order.serve();
        return order;
    }

    public static Order completedOrder() {
        Order order = servedOrder();
        order.complete();

        return order;
    }


    public static OrderLineItems orderLineItems() {
        OrderLineItem orderLineItem1 = new kitchenpos.eatinorders.tobe.domain.OrderLineItem(UUID.randomUUID(),
            new OrderLineItemQuantity(1));
        OrderLineItem orderLineItem2 = new kitchenpos.eatinorders.tobe.domain.OrderLineItem(UUID.randomUUID(),
            new OrderLineItemQuantity(1));

        return new OrderLineItems(List.of(orderLineItem1, orderLineItem2));
    }

    public static OrderTable occupiedOrderTable(Orders orders) {
        OrderTable orderTable = new OrderTable(new OrderTableName("name"), orders);
        orderTable.occupy();

        return orderTable;
    }

    public static OrderTable occupiedOrderTable() {
        OrderTable orderTable = new OrderTable(new OrderTableName("name"));
        orderTable.occupy();

        return orderTable;
    }

}
