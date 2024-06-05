package kitchenpos.supports.domain.tobe;

import jakarta.persistence.Embeddable;
import java.util.Objects;

public class Quantity {
  private long quantity;

  protected Quantity() {
  }

  public Quantity(long quantity) {
    validate(quantity);
    this.quantity = quantity;
  }

  private void validate(long quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException();
    }
  }

  public long getQuantity() {
    return quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Quantity that = (Quantity) o;
    return quantity == that.quantity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity);
  }

  @Override
  public String toString() {
    return "Quantity{" +
        "quantity=" + quantity +
        '}';
  }
}
