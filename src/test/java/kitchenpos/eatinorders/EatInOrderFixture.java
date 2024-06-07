package kitchenpos.eatinorders;

import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderType;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class EatInOrderFixture {
    private static Random random = new Random();

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
                random.nextLong(), quantity, price, UUID.randomUUID(), true, BigDecimal.ONE
        );
        return orderLineItem;
    }

    public static OrderLineItem orderLineItemOf(long quantity, BigDecimal price, UUID menuId) {
        OrderLineItem orderLineItem = new OrderLineItem(
                random.nextLong(), quantity, price, menuId, true, BigDecimal.ONE
        );
        return orderLineItem;
    }

    public static OrderLineItem orderLineItemOf(long quantity, BigDecimal price, UUID menuId,
                                                boolean isDisplayedMenu, BigDecimal menuPrice) {
        OrderLineItem orderLineItem = new OrderLineItem(
                random.nextLong(), quantity, price, menuId, isDisplayedMenu, menuPrice
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

    public static EatInOrder eatInOrderOf(EatInOrderStatus status, OrderLineItems orderLineItems, UUID orderTableId) {
        EatInOrder eatInOrder = new EatInOrder(
                UUID.randomUUID(), EatInOrderType.EAT_IN, status,
                LocalDateTime.now(), orderLineItems, orderTableId
        );
        return eatInOrder;
    }
}
