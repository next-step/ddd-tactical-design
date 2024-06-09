package kitchenpos.eatinorders.tobe.domain.order.dto;

import java.util.List;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;

public class OrderCreateCommand {

  private final UUID orderTableId;
  private final List<OrderLineItemCreateCommand> orderLineItems;

  public OrderCreateCommand(UUID orderTableId,
      List<OrderLineItemCreateCommand> orderLineItems) {
    this.orderTableId = orderTableId;
    this.orderLineItems = orderLineItems;
  }

  public UUID getOrderTableId() {
    return orderTableId;
  }

  public List<OrderLineItemCreateCommand> getOrderLineItems() {
    return orderLineItems;
  }

}
