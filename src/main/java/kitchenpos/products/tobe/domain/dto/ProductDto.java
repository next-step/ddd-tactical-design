package kitchenpos.products.tobe.domain.dto;

import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {
  private UUID id;
  private String name;
  private BigDecimal price;

  public ProductDto(UUID id, String name, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public static ProductDto of(Product product) {
    DisplayedName displayedName = product.getDisplayedName();
    ProductPrice productPrice = product.getProductPrice();
    return new ProductDto(product.getId(), displayedName.getName(), productPrice.getPrice());
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
