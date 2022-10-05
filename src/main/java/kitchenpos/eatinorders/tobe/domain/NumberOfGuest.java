package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

public class NumberOfGuest {

  public static final NumberOfGuest EMPTY = NumberOfGuest.from(0);

  private final int number;

  public static NumberOfGuest from(int number) {
    return new NumberOfGuest(number);
  }

  private NumberOfGuest(int number) {
    if (number < 0) {
      throw new IllegalArgumentException();
    }
    this.number = number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NumberOfGuest that = (NumberOfGuest) o;
    return number == that.number;
  }

  @Override
  public int hashCode() {
    return Objects.hash(number);
  }
}
