package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

public class Price {

  private final BigDecimal amount;

  private Price(BigDecimal amount) {
    this.amount = amount;
  }

  public static Price from(BigDecimal amount) {
    return new Price(amount);
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
