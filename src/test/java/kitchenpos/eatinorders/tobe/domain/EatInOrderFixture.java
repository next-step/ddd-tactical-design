package kitchenpos.eatinorders.tobe.domain;

import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.eatinorders.tobe.domain.MenuFixture.*;

public class EatInOrderFixture {
    public static List<OrderLineItem> ORDER_LINE_ITEMS;
    public static OrderTable ORDER_TABLE;

    static {
        ORDER_LINE_ITEMS = Arrays.asList(
            new OrderLineItem(displayedMenu(10_000), 1L, BigDecimal.valueOf(10_000)),
            new OrderLineItem(displayedMenu(12_000), 2L, BigDecimal.valueOf(12_000))
        );

        ORDER_TABLE = new OrderTable("1번");
        ORDER_TABLE.sit();
        ORDER_TABLE.changeNumberOfGuests(3);
    }

    public static EatInOrder eatInOrder() {
        return new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE);
    }

    public static EatInOrder eatInOrder(OrderTable orderTable) {
        return new EatInOrder(ORDER_LINE_ITEMS, orderTable);
    }

    public static EatInOrder eatInOrder(OrderStatus orderStatus) {
        return eatInOrder(orderStatus, ORDER_TABLE);
    }

    public static EatInOrder eatInOrder(OrderStatus orderStatus, OrderTable orderTable) {
        EatInOrder eatInOrder = new EatInOrder(ORDER_LINE_ITEMS, orderTable);
        ReflectionTestUtils.setField(eatInOrder, "status", orderStatus);
        return eatInOrder;
    }
}
