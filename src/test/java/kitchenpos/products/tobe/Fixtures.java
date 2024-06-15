package kitchenpos.products.tobe;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.EatInOrder;

public class Fixtures {
  public static final UUID INVALID_ID = new UUID(0L, 0L);

  public static EatInOrder order(final EatInOrderStatus status, final String deliveryAddress) {
    final EatInOrder order = new EatInOrder();
    order.setId(UUID.randomUUID());
    order.setType(EatInOrderType.DELIVERY);
    order.setStatus(status);
    order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
    order.setOrderLineItems(Arrays.asList(orderLineItem()));
    order.setDeliveryAddress(deliveryAddress);
    return order;
  }

  public static EatInOrder order(final EatInOrderStatus status) {
    final EatInOrder order = new EatInOrder();
    order.setId(UUID.randomUUID());
    order.setType(EatInOrderType.TAKEOUT);
    order.setStatus(status);
    order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
    order.setOrderLineItems(Arrays.asList(orderLineItem()));
    return order;
  }

  public static EatInOrder order(final EatInOrderStatus status, final EatInOrderTable orderTable) {
    final EatInOrder order = new EatInOrder();
    order.setId(UUID.randomUUID());
    order.setType(EatInOrderType.EAT_IN);
    order.setStatus(status);
    order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
    order.setOrderLineItems(Arrays.asList(orderLineItem()));
    order.setOrderTable(orderTable);
    return order;
  }

  public static EatInOrderLineItem orderLineItem() {
    final EatInOrderLineItem orderLineItem = new EatInOrderLineItem();
    orderLineItem.setSeq(new Random().nextLong());
    // orderLineItem.setMenu(menu());
    return orderLineItem;
  }

  public static EatInOrderTable orderTable() {
    return orderTable(false, 0);
  }

  public static EatInOrderTable orderTable(final boolean occupied, final int numberOfGuests) {
    final EatInOrderTable orderTable = new EatInOrderTable();
    orderTable.setId(UUID.randomUUID());
    orderTable.setName("1번");
    orderTable.setNumberOfGuests(numberOfGuests);
    orderTable.setOccupied(occupied);
    return orderTable;
  }
}
