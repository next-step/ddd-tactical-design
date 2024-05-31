package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
  private BigDecimal price;

  protected MenuPrice() {
  }

  public MenuPrice(BigDecimal price) {
    validate(price);
    this.price = price;
  }

  private static void validate(BigDecimal price) {
    if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException();
    }
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuPrice menuPrice = (MenuPrice) o;
    return Objects.equals(price, menuPrice.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }

  @Override
  public String toString() {
    return "MenuPrice{" +
        "price=" + price +
        '}';
  }
}
