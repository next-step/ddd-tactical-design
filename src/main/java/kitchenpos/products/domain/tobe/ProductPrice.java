package kitchenpos.products.domain.tobe;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;


@Embeddable
public class ProductPrice {

  private BigDecimal price;

  protected ProductPrice() {
  }

  private ProductPrice(BigDecimal price) {
    validate(price);
    this.price = price;
  }

  public static ProductPrice from(Long price) {
    if (Objects.isNull(price)) {
      throw new IllegalArgumentException("상품의 가격이 올바르지 않으면 변경할 수 없다.");
    }
    return new ProductPrice(BigDecimal.valueOf(price));
  }

  public static ProductPrice from(final BigDecimal price) {
    return new ProductPrice(price);
  }

  private void validate(BigDecimal price) {
    if (Objects.isNull(price)) {
      throw new IllegalArgumentException("상품의 가격이 올바르지 않으면 변경할 수 없다.");
    }

    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("상품의 가격은 0원 이상이어야 한다.");
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
}
