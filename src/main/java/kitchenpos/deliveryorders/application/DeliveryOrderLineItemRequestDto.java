package kitchenpos.deliveryorders.application;

import java.math.BigDecimal;
import java.util.UUID;

public class DeliveryOrderLineItemRequestDto {
  private UUID menuId;
  private BigDecimal price;
  private long quantity;

  public DeliveryOrderLineItemRequestDto() {}

  public DeliveryOrderLineItemRequestDto(UUID menuId, BigDecimal price, long quantity) {
    this.menuId = menuId;
    this.price = price;
    this.quantity = quantity;
  }

  public UUID getMenuId() {
    return menuId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public long getQuantity() {
    return quantity;
  }
}
