package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.Menu;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItems;
import kitchenpos.eatinorders.tobe.ui.OrderTableForm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
        return createOrderLineItem(5L);
    }

    public static TobeOrderLineItem createOrderLineItem(Long quantity) {
        return new TobeOrderLineItem(1L, createMenu(), quantity);
    }

    public static TobeOrderLineItem createOrderLineItem(Menu menu, Long quantity) {
        return new TobeOrderLineItem(1L, menu, quantity);
    }

    public static TobeOrderLineItems createOrderLineItems() {
        List<TobeOrderLineItem> list = new ArrayList<>();
        list.add(createOrderLineItem());
        list.add(createOrderLineItem());
        list.add(createOrderLineItem());
        return new TobeOrderLineItems(list);
    }

    public static TobeOrderLineItems createOrderLineItems(TobeOrderLineItem... orderLineItems) {
        return new TobeOrderLineItems(Arrays.asList(orderLineItems));
    }

    public static TobeOrder createOrder() {
        return createOrder(OrderType.EAT_IN);
    }

    public static TobeOrder createOrder(OrderType type) {
        return createOrder(type, createOrderLineItems());
    }

    public static TobeOrder createOrder(OrderType type, TobeOrderLineItems orderLineItemss) {
        return createOrder(type, "서울", orderLineItemss);
    }

    public static TobeOrder createOrder(OrderType type, String address, TobeOrderLineItems orderLineItemss) {
        return new TobeOrder(
                type,
                orderLineItemss,
                address,
                UUID.randomUUID()
        );
    }

    public static OrderTableForm createOrderTableForm() {
        return createOrderTableForm("1번");
    }

    public static OrderTableForm createOrderTableForm(final String name) {
        final OrderTableForm orderTable = new OrderTableForm();
        orderTable.setName(name);
        return orderTable;
    }
}
