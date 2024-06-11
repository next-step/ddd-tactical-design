package kitchenpos.menus.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
  @Column(name = "price", nullable = false)
  private BigDecimal price;

  protected MenuPrice() {}

  protected MenuPrice(BigDecimal price) {
    if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException();
    }

    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
