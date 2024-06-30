package kitchenpos.takeoutorders.application;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.takeoutorders.domain.*;

public class TakeoutOrderFixtures {

  public static TakeoutOrder order() {
    return TakeoutOrder.createOrder(List.of(orderLineItem()));
  }

  public static TakeoutOrderLineItem orderLineItem() {
    return TakeoutOrderLineItem.createItem(new Random().nextLong(10), UUID.randomUUID());
  }
}
