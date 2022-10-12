package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

  private final UUID id;

  private OrderStatus status;

  private final LocalDateTime orderDateTime;

  private final UUID orderTableId;

  private final OrderLineItems orderLineItems;

  public Order(UUID id, UUID orderTableId, List<OrderLineItem> orderLineItems) {
    this(id, orderTableId, OrderStatus.WAITING, new OrderLineItems(orderLineItems));
  }

  public Order(UUID id, UUID orderTableId, OrderStatus status, List<OrderLineItem> orderLineItems) {
    this(id, orderTableId, status, new OrderLineItems(orderLineItems));
  }

  public Order(UUID id, UUID orderTableId, OrderStatus status, OrderLineItems orderLineItems) {
    this.id = id;
    this.status = status;
    this.orderDateTime = LocalDateTime.now();
    this.orderTableId = orderTableId;
    this.orderLineItems = orderLineItems;;
  }

  public void accept() {
    if (!status.isWaiting()) {
      throw new IllegalStateException();
    }
    this.status = OrderStatus.ACCEPTED;
  }

  public void serve() {
    if (!status.isAccepted()) {
      throw new IllegalStateException();
    }
    this.status = OrderStatus.SERVED;
  }

  public void complete() {
    if (!status.isServed()) {
      throw new IllegalStateException();
    }
    this.status = OrderStatus.COMPLETED;
  }
}
