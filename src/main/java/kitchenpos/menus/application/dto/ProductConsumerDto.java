package kitchenpos.menus.application.dto;

import java.util.UUID;

public class ProductConsumerDto {
  private final UUID productId;
  private final Long quantity;

  public ProductConsumerDto(UUID productId, Long quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public UUID getProductId() {
    return productId;
  }

  public Long getQuantity() {
    return quantity;
  }
}
