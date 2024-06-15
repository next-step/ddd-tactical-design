package kitchenpos.eatinorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Quantity {
  @Column(name = "quantity", nullable = false)
  private long quantity;

  protected Quantity() {}

  protected Quantity(final long quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException();
    }

    this.quantity = quantity;
  }

  public long getQuantity() {
    return quantity;
  }
}
