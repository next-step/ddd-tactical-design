package kitchenpos.eatinorders.tobe.domain.order.event;

import java.util.Objects;
import java.util.UUID;

public class OrderCompletionEvent {

  private final UUID orderTableId;

  public OrderCompletionEvent(UUID orderTableId) {
    this.orderTableId = Objects.requireNonNull(orderTableId);
  }

  public UUID getOrderTableId() {
    return orderTableId;
  }
}
