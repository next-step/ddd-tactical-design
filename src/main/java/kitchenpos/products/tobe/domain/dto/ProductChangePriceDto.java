package kitchenpos.products.tobe.domain.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductChangePriceDto {
  private UUID id;
  private BigDecimal price;

  public ProductChangePriceDto(UUID id, BigDecimal price) {
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
