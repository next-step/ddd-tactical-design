package kitchenpos.eatinorders;

import static kitchenpos.menus.MenuFixtures.menu;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;

public class OrderFixtures {

    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(
            UUID.randomUUID(),
            new OrderTableName("1번"),
            new NumberOfGuests(numberOfGuests),
            occupied
        );
    }

    public static Order eatInOrder(final OrderStatus status, final OrderTable orderTable) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setOrderTable(orderTable);
        return order;
    }

    public static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu());
        return orderLineItem;
    }
}
