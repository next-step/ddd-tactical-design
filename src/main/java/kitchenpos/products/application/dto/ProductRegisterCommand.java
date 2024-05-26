package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public class ProductRegisterCommand {
  private String name;
  private BigDecimal price;

  public ProductRegisterCommand(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
