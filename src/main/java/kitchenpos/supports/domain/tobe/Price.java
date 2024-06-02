package kitchenpos.supports.domain.tobe;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {
  public static final Price ZERO = new Price(BigDecimal.ZERO);
  private BigDecimal price;

  protected Price() {
  }

  public Price(BigDecimal price) {
    validate(price);
    this.price = price;
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
    Price price1 = (Price) o;
    return Objects.equals(price, price1.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }

  @Override
  public String toString() {
    return "Price{" +
        "price=" + price +
        '}';
  }

  public Price multiply(Quantity quantity) {
    BigDecimal newPrice = this.price.multiply(new BigDecimal(quantity.getQuantity()));
    return new Price(newPrice);
  }

  public Price add(Price price) {
    BigDecimal newPrice = this.price.add(price.getPrice());
    return new Price(newPrice);
  }

  public boolean isLessOrEqualThen(Price other) {
    return compareTo(other) <= 0;
  }

  private int compareTo(Price other) {
    return this.price.compareTo(other.getPrice());
  }
}
