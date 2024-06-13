package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;

public class OrderTableNumberOfGuests {
  public final static OrderTableNumberOfGuests ABSENT = new OrderTableNumberOfGuests(0);
  private int numberOfGuests;

  protected OrderTableNumberOfGuests() {
  }

  public OrderTableNumberOfGuests(int numberOfGuests) {
    validate(numberOfGuests);
    this.numberOfGuests = numberOfGuests;
  }

  private void validate(int numberOfGuests) {
    if (numberOfGuests < 0) {
      throw new IllegalArgumentException();
    }
  }

  public int getNumberOfGuests() {
    return numberOfGuests;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderTableNumberOfGuests that = (OrderTableNumberOfGuests) o;
    return numberOfGuests == that.numberOfGuests;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfGuests);
  }

  @Override
  public String toString() {
    return "OrderTableNumberOfGuests{" +
        "numberOfGuests=" + numberOfGuests +
        '}';
  }
}
