package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
  private BigDecimal price;

  public ProductPrice(BigDecimal price) {
    validate(price);
    this.price = price;
  }

  protected ProductPrice() {
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
    ProductPrice that = (ProductPrice) o;
    return Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }

  @Override
  public String toString() {
    return "ProductPrice{" +
        "price=" + price +
        '}';
  }

}
