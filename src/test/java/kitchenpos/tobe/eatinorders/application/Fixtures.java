package kitchenpos.tobe.eatinorders.application;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrder;
import kitchenpos.eatinorders.tobe.domain.model.DeliveryOrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.Menu;
import kitchenpos.eatinorders.tobe.domain.model.NumberOfGuests;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrder;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrderStatus;
import kitchenpos.products.domain.Product;

public class Fixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);
    public static final UUID HIDE_MENU_ID = new UUID(5L, 5L);

    public static final UUID EMPTY_TABLE_ID = new UUID(6L, 6L);

    public static final UUID NOT_COMPLETED_ORDER_TABLE_ID = new UUID(7L, 7L);

    public static Menu menu() {
        return menu(19_000L, true);
    }

    public static Menu menu(final long price, final boolean displayed) {
        final Menu menu = new Menu(UUID.randomUUID(), "후라이드+후라이드", BigDecimal.valueOf(price), displayed);
        return menu;
    }

    public static Menu hideMenu(final long price, final boolean displayed) {
        final Menu menu = new Menu(HIDE_MENU_ID, "후라이드+후라이드", BigDecimal.valueOf(price), displayed);
        return menu;
    }

    public static DeliveryOrder deliveryOrder(final String deliveryAddress) {
        final DeliveryOrder order = new DeliveryOrder(Arrays.asList(orderLineItem()), deliveryAddress);
        return order;
    }

    public static DeliveryOrder deliveryOrder(final DeliveryOrderStatus status, final String deliveryAddress) {
        final DeliveryOrder order = new DeliveryOrder(Arrays.asList(orderLineItem()), deliveryAddress);
        try {
            final Field statusField = order.getClass().getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, status);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return order;
    }

    public static EatInOrder eatInOrder(final EatInOrderStatus status, final UUID orderTableId) {
        final EatInOrder order = new EatInOrder(Arrays.asList(orderLineItem()), orderTableId);
        try {
            final Field statusField = order.getClass().getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, status);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return order;
    }

    public static TakeOutOrder takeOutOrder(final TakeOutOrderStatus status) {
        final TakeOutOrder order = new TakeOutOrder(Arrays.asList(orderLineItem()));
        try {
            final Field statusField = order.getClass().getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, status);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return order;
    }

    public static OrderLineItem createOrderLineItemRequest(final UUID menuId, final long price, final long quantity) {
        return new OrderLineItem(menuId, BigDecimal.valueOf(price), quantity);
    }

    public static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem(menu().getId(), menu().getPrice(), 1);
        return orderLineItem;
    }

    public static OrderTable orderTable() {
        return orderTable(true, 0);
    }

    public static OrderTable orderTable(final boolean empty, final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable("1번");

        try {
            final Field emptyField = orderTable.getClass().getDeclaredField("empty");
            final Field numberOfGuestsField = orderTable.getClass().getDeclaredField("numberOfGuests");
            emptyField.setAccessible(true);
            numberOfGuestsField.setAccessible(true);
            emptyField.set(orderTable, empty);
            numberOfGuestsField.set(orderTable, new NumberOfGuests(numberOfGuests));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return orderTable;
    }

    public static OrderTable notCompletedOrderTable() {
        final OrderTable orderTable = new OrderTable("1번");

        try {
            final Field id = orderTable.getClass().getDeclaredField("id");
            id.setAccessible(true);
            id.set(orderTable, NOT_COMPLETED_ORDER_TABLE_ID);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return orderTable;
    }

}
