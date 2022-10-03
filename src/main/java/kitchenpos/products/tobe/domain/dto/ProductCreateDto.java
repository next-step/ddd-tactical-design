package kitchenpos.products.tobe.domain.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateDto {
  private String name;
  private BigDecimal price;

  public ProductCreateDto(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public Product toEntity(Boolean isProfanity) {
    return new Product(name, isProfanity, price);
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
