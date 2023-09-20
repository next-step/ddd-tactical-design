package kitchenpos.eatin_orders;

import kitchenpos.eatinorders.domain.orders.EatInOrder;
import kitchenpos.eatinorders.domain.orders.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.orders.OrderStatus;
import kitchenpos.eatinorders.domain.ordertables.NumberOfGuests;
import kitchenpos.eatinorders.domain.ordertables.OrderTable;
import kitchenpos.eatinorders.domain.ordertables.OrderTableName;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;

public class EatInOrderFixtures {

    public static EatInOrder order(final OrderStatus status, final OrderTable orderTable, final ProductRepository productRepository) {
        final EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(List.of(eatInOrderLineItem(productRepository)));
        order.setOrderTable(orderTable);
        return order;
    }

    public static EatInOrderLineItem eatInOrderLineItem(final ProductRepository productRepository) {
        final EatInOrderLineItem orderLineItem = new EatInOrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu(productRepository));
        return orderLineItem;
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        return new OrderTable(UUID.randomUUID(), new OrderTableName("1번"), new NumberOfGuests(numberOfGuests), occupied);
    }
}
