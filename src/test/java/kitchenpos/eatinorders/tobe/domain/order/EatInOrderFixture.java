package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;
import kitchenpos.supports.domain.tobe.PriceFixture;
import org.springframework.test.util.ReflectionTestUtils;

public class EatInOrderFixture {
  public static EatInOrder create(UUID orderTableId, List<EatInOrderLineItem> orderLineItems, EatInOrderStatus status) {
    EatInOrder eatInOrder = new EatInOrder(orderTableId, orderLineItems);
    ReflectionTestUtils.setField(eatInOrder, "status", status);
    return eatInOrder;
  }

  public static EatInOrder create(EatInOrderStatus status) {
    return create(UUID.randomUUID(), List.of(normalEatInOrderLineItem()), status);
  }

  public static EatInOrderLineItem createEatInOrderLineItem(EatInOrderLineItemQuantity quantity, UUID menuId, Price price) {
    return new EatInOrderLineItem(quantity, menuId, price);
  }

  public static EatInOrderLineItem normalEatInOrderLineItem() {
    return createEatInOrderLineItem(normalEatInOrderLineItemQuantity(), UUID.randomUUID(),
        PriceFixture.normal());
  }

  public static EatInOrderLineItemQuantity normalEatInOrderLineItemQuantity() {
    return new EatInOrderLineItemQuantity(3L);
  }
}
