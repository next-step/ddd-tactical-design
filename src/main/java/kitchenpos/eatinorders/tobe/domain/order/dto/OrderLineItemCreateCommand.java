package kitchenpos.eatinorders.tobe.domain.order.dto;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemQuantity;
import kitchenpos.supports.domain.tobe.Price;

public class OrderLineItemCreateCommand {

  private final UUID menuId;
  private final EatInOrderLineItemQuantity quantity;
  private final Price price;

  public OrderLineItemCreateCommand(UUID menuId, EatInOrderLineItemQuantity quantity, Price price) {
    this.menuId = menuId;
    this.quantity = quantity;
    this.price = price;
  }

  public UUID getMenuId() {
    return menuId;
  }

  public EatInOrderLineItemQuantity getQuantity() {
    return quantity;
  }

  public Price getPrice() {
    return price;
  }
}
