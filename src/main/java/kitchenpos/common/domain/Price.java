package kitchenpos.common.domain;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

  private BigDecimal price;

  protected Price() {
  }

  private Price(BigDecimal price) {
    validate(price);
    this.price = price;
  }

  public static Price from(Long price) {
    if (Objects.isNull(price)) {
      throw new IllegalArgumentException("상품의 가격이 올바르지 않으면 변경할 수 없다.");
    }
    return new Price(BigDecimal.valueOf(price));
  }

  public static Price from(final BigDecimal price) {
    return new Price(price);
  }

  private void validate(BigDecimal price) {
    if (Objects.isNull(price)) {
      throw new IllegalArgumentException("가격이 올바르지 않으면 변경할 수 없다.");
    }

    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("가격은 0원 이상이어야 한다.");
    }
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal multiply(BigDecimal quantity) {
    return getPrice().multiply(quantity);
  }

  public boolean isBigger(BigDecimal price){
    return getPrice().compareTo(price) > 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Price price1 = (Price) o;
    return Objects.equals(price, price1.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }
}
