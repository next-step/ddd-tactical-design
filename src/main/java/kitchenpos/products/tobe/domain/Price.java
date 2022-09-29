package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

  @Column(name = "price", nullable = false)
  private BigDecimal amount;

  public static Price from(long amount) {
    return from(BigDecimal.valueOf(amount));
  }

  public static Price from(BigDecimal amount) {
    return new Price(amount);
  }

  public Price() {

  }

  private Price(BigDecimal amount) {
    if (Objects.isNull(amount) || amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException();
    }
    this.amount = amount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Price price = (Price) o;
    return Objects.equals(amount, price.amount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount);
  }
}
