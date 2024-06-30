package kitchenpos.eatinorders.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
  @Transient private BigDecimal price;

  protected Price() {}

  protected Price(final BigDecimal price) {
    if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException();
    }

    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }
}
