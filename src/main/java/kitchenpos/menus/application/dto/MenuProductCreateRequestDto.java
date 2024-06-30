package kitchenpos.menus.application.dto;

import java.util.UUID;

public class MenuProductCreateRequestDto {
  private final UUID productId;
  private final Long quantity;

  public MenuProductCreateRequestDto(UUID productId, Long quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public UUID getProductId() {
    return productId;
  }

  public Long getQuantity() {
    return quantity;
  }

  public ProductConsumerDto to() {
    return new ProductConsumerDto(this.productId, this.quantity);
  }
}
