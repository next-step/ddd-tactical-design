package kitchenpos.eatinorders;

import kitchenpos.eatinorders.tobe.OrderLineItem;
import kitchenpos.eatinorders.tobe.OrderTable;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class EatInOrderFixture {
    public OrderTable 주문_테이블 = emptyOrderTableOf("주문_테이블");

    public static OrderTable emptyOrderTableOf(String name) {
        OrderTable orderTable = new OrderTable(
                UUID.randomUUID(),
                name,
                0,
                false
        );
        return orderTable;
    }

    public static OrderTable sitOrderTableOf(String name) {
        OrderTable orderTable = new OrderTable(
                UUID.randomUUID(),
                name,
                0,
                false
        );
        orderTable.sit();
        return orderTable;
    }

    public static OrderLineItem orderLineItemOf(long quantity, BigDecimal price) {
        OrderLineItem orderLineItem = new OrderLineItem(
                new Random().nextLong(), quantity, price
        );
        return orderLineItem;
    }
}
