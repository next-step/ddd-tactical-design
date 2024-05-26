package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductPriceChangeCommand {
  private UUID id;
  private BigDecimal price;

  public ProductPriceChangeCommand(UUID id, BigDecimal price) {
    this.id = id;
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
