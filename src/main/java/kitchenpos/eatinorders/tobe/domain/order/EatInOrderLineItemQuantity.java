package kitchenpos.eatinorders.tobe.domain.order;

import java.util.Objects;

public class EatInOrderLineItemQuantity {

  private long quantity;

  protected EatInOrderLineItemQuantity() {
  }

  public EatInOrderLineItemQuantity(long quantity) {
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EatInOrderLineItemQuantity that = (EatInOrderLineItemQuantity) o;
    return quantity == that.quantity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity);
  }

  @Override
  public String toString() {
    return "EatInOrderLineItemQuantity{" +
        "quantity=" + quantity +
        '}';
  }
}
