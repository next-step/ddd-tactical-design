package kitchenpos.menus.domain.menu;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuProductPrice {
  private BigDecimal productPrice;

  protected MenuProductPrice() {}

  protected MenuProductPrice(final BigDecimal productPrice) {
    if (Objects.isNull(productPrice) || productPrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException();
    }

    this.productPrice = productPrice;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }
}
