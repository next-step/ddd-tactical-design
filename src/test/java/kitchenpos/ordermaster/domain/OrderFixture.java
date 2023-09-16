package kitchenpos.ordermaster.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import kitchenpos.eatinorders.domain.OrderType;

public class OrderFixture {
    public static ToBeOrderLineItems createOrderLineItems() {
        return new ToBeOrderLineItems(List.of(createOrderLineIterm()));
    }

    public static ToBeOrderLineItem createOrderLineIterm() {
        return new ToBeOrderLineItem(createOrderMenu(), OrderQuantity.of(3, OrderType.TAKEOUT));
    }

    public static OrderMenu createOrderMenu() {
        return new OrderMenu(UUID.randomUUID(), BigDecimal.valueOf(13_000L));
    }
}
