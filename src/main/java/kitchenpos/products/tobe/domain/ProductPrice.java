package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice {

  @Column(name = "price", nullable = false)
  private BigDecimal value;

  protected ProductPrice() {

  }

  public ProductPrice(BigDecimal value) {
    if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("가격을 확인해 주세요.");
    }
    this.value = value;
  }

  public ProductPrice(long value) {
    this(BigDecimal.valueOf(value));
  }

  public BigDecimal getValue() {
    return value;
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

    return value != null ? value.equals(that.value) : that.value == null;
  }

  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : 0;
  }
}
