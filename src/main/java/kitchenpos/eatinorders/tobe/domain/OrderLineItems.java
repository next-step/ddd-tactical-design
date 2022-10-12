package kitchenpos.eatinorders.tobe.domain;

import java.util.Collections;
import java.util.List;

public class OrderLineItems {

  private final List<OrderLineItem> orderLineItems;

  public OrderLineItems(List<OrderLineItem> orderLineItems) {
    this.orderLineItems = orderLineItems;
  }

  public List<OrderLineItem> getOrderLineItems() {
    return Collections.unmodifiableList(orderLineItems);
  }
}
