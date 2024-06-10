package kitchenpos.common.domain.products;

import java.util.UUID;

public class ProductPriceChangeEvent {
  private final UUID productId;
  private final Long price;

  public ProductPriceChangeEvent(UUID productId, Long price) {
    this.productId = productId;
    this.price = price;
  }

  public UUID getProductId() {
    return productId;
  }

  public Long getPrice() {
    return price;
  }
}
