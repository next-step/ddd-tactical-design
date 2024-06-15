package kitchenpos.eatinorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {
  @Column(name = "number_of_guests", nullable = false)
  private int numberOfGuests;

  protected NumberOfGuests() {}

  protected NumberOfGuests(final int numberOfGuests) {
    if (numberOfGuests < 0) {
      throw new IllegalArgumentException();
    }

    this.numberOfGuests = numberOfGuests;
  }

  public int getNumberOfGuests() {
    return numberOfGuests;
  }
}
