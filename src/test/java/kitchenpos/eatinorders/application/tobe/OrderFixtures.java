package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.Menu;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItems;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderFixtures {
    public static Menu createMenu() {
        return createMenu("치킨");
    }

    public static Menu createMenu(String name) {
        return createMenu(name, BigDecimal.TEN);
    }

    public static Menu createMenu(BigDecimal price) {
        return createMenu("치킨", price);
    }

    public static Menu createMenu(String name, BigDecimal price) {
        return new Menu(UUID.randomUUID(), name, price, true);
    }

    public static TobeOrderLineItem createOrderLineItem() {
        return new TobeOrderLineItem(1L, createMenu(), 5);
    }

    public static TobeOrderLineItems createOrderLineItems() {
        List<TobeOrderLineItem> list = new ArrayList<>();
        list.add(createOrderLineItem());
        list.add(createOrderLineItem());
        list.add(createOrderLineItem());
        return new TobeOrderLineItems(list);
    }

    public static TobeOrder createOrder() {
        return new TobeOrder(
                OrderType.EAT_IN,
                createOrderLineItems(),
                "서울",
                UUID.randomUUID()
        );
    }
}
