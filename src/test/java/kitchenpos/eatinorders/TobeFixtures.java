package kitchenpos.eatinorders;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.Price;

public final class TobeFixtures {
    public static final long ORDER_LINE_ITEM_PRICE = 10_000;
    public static final long ORDER_LINE_ITEM_QUANTITY = 2;

    public static OrderTable orderTable() {
        return orderTable(false);
    }

    public static OrderTable orderTable(boolean occupied) {
        if (occupied) {
            OrderTable orderTable = new OrderTable("테이블");
            orderTable.sit();
            return orderTable;
        }
        return new OrderTable("테이블");
    }

    public static OrderLineItem orderLineItem() {
        return orderLineItem(true);
    }

    public static OrderLineItem orderLineItem(boolean displayed) {
        return new OrderLineItem(
            UUID.randomUUID().toString(),
            new Price(new BigDecimal(ORDER_LINE_ITEM_PRICE)),
            ORDER_LINE_ITEM_QUANTITY,
            displayed);
    }
}
