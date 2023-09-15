package kitchenpos;

import kitchenpos.eatinorders.domain.order.EatInOrder;
import kitchenpos.eatinorders.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.order.EatInOrderStatus;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.menus.tobe.domain.menu.MenuId;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.menus.application.fixtures.MenuFixture.menu;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);
    public static final MenuId INVALID_MENU_ID = new MenuId(new UUID(0L, 0L));

    public static EatInOrder order(final EatInOrderStatus status, final String deliveryAddress) {
        final EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.DELIVERY);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static EatInOrder order(final EatInOrderStatus status) {
        final EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.TAKEOUT);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        return order;
    }

    public static EatInOrder order(final EatInOrderStatus status, final OrderTable orderTable) {
        final EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setOrderTable(orderTable);
        return order;
    }

    public static EatInOrderLineItem orderLineItem() {
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem();
        eatInOrderLineItem.setSeq(new Random().nextLong());
        eatInOrderLineItem.setMenu(menu());
        return eatInOrderLineItem;
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName("1번");
        orderTable.setNumberOfGuests(numberOfGuests);
        orderTable.setOccupied(occupied);
        return orderTable;
    }

}
