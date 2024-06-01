package kitchenpos.eatinorders;

import kitchenpos.eatinorders.tobe.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class EatInOrderFixture {
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

    public static EatInOrder eatInOrderOf(OrderLineItems orderLineItems, UUID orderTableId) {
        EatInOrder eatInOrder = new EatInOrder(
                UUID.randomUUID(), EatInOrderType.EAT_IN, EatInOrderStatus.WAITING,
                LocalDateTime.now(), orderLineItems, orderTableId
        );
        return eatInOrder;
    }
}
