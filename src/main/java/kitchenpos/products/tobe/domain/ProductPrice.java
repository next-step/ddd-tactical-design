package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

  private static final BigDecimal ZERO = BigDecimal.ZERO;

  private BigDecimal price;

  protected ProductPrice() {/*no-op*/}

  public ProductPrice(BigDecimal price) {
    validate(price);
    this.price = price;
  }

  private void validate(BigDecimal price) {
    if (price == null || price.compareTo(ZERO) < 0) {
      throw new IllegalArgumentException("가격은 0원 이상이어야 합니다.");
    }
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
