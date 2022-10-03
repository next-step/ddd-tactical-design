package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
  private static BigDecimal MINIMUM_PRICE = BigDecimal.ZERO;

  @Column(name = "price")
  private BigDecimal price;

  public ProductPrice() {}

  public ProductPrice(BigDecimal price) {
    if (price == null || price.compareTo(MINIMUM_PRICE) < 0) {
      throw new IllegalArgumentException("잘못된 상품가격입니다.");
    }

    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductPrice that = (ProductPrice) o;
    return Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }
}
