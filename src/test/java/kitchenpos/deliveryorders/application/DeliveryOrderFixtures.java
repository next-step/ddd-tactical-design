package kitchenpos.deliveryorders.application;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.deliveryorders.domain.*;

public class DeliveryOrderFixtures {

  public static DeliveryOrder order(final String deliveryAddress) {
    return DeliveryOrder.createOrder(
        Arrays.asList(DeliveryOrderFixtures.orderLineItem()), deliveryAddress);
  }

  public static DeliveryOrderLineItem orderLineItem() {
    return DeliveryOrderLineItem.createItem(
        new Random().nextLong(10), UUID.randomUUID(), BigDecimal.valueOf(10_000L));
  }
}
