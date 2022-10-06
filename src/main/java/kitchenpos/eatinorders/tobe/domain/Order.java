package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

  private final UUID id;

  private OrderStatus status;

  private final LocalDateTime orderDateTime;

  private final UUID orderTableId;

  private final List<OrderLineItem> orderLineItems;

  public Order(UUID id, UUID orderTableId, List<OrderLineItem> orderLineItems) {
    this.id = id;
    this.status = OrderStatus.WAITING;
    this.orderDateTime = LocalDateTime.now();
    this.orderTableId = orderTableId;
    this.orderLineItems = orderLineItems;
  }
}
