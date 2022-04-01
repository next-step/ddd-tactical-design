package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

  private static final String PRICE_MUST_BE_POSITIVE_NUMBER = "가격은 0 원 이상이어야 합니다. 입력 값 : %d";

  private BigDecimal price;

  protected Price() {}

  public Price(BigDecimal price) {
    validatePrice(price);
    this.price = price;
  }

  private void validatePrice(BigDecimal price) {
    if (isNegative(price)) {
      throw new IllegalArgumentException(String.format(PRICE_MUST_BE_POSITIVE_NUMBER, price.longValue()));
    }
  }

  private boolean isNegative(BigDecimal price) {
    return price.compareTo(BigDecimal.ZERO) < 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Price price1 = (Price) o;
    return Objects.equals(price, price1.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }
}
