package kitchenpos.eatinorders.application;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.domain.EatInOrder;

public class EatInOrderFixtures {
  public static EatInOrder order(final String name) {
    return EatInOrder.createOrder(
        Arrays.asList(EatInOrderFixtures.orderLineItem()), EatInOrderFixtures.orderTable(name));
  }

  public static EatInOrderLineItem orderLineItem() {
    return EatInOrderLineItem.createItem(new Random().nextLong(), UUID.randomUUID());
  }

  public static EatInOrderTable orderTable(final String name) {
    return orderTable(name, false, 0);
  }

  public static EatInOrderTable orderTable(
      final String name, final boolean occupied, final int numberOfGuests) {
    return EatInOrderTable.createOrderTable(name, numberOfGuests, occupied);
  }

  public static EatInOrderRequestDto eatInOrderRequestDto(
      final UUID eatInOrderTableId,
      final List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDtos) {
    return new EatInOrderRequestDto(eatInOrderTableId, eatInOrderLineItemRequestDtos);
  }

  public static List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDto(
      UUID menuId, BigDecimal price, long quantity) {
    return List.of(new EatInOrderLineItemRequestDto(menuId, price, quantity));
  }
}
