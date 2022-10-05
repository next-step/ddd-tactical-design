package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class OrderTable {

  private final UUID id;
  private final OrderTableName name;
  private NumberOfGuest numberOfGuests;
  private boolean occupied;

  public OrderTable(UUID id, String name) {
    this(id, OrderTableName.from(name), NumberOfGuest.EMPTY, false);
  }

  public OrderTable(UUID id, String name, int numberOfGuests) {
    this(id, OrderTableName.from(name), numberOfGuests, false);
  }

  public OrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
    this(id, OrderTableName.from(name), numberOfGuests, occupied);
  }

  public OrderTable(UUID id, OrderTableName name, int numberOfGuests, boolean occupied) {
    this.id = id;
    this.name = name;
    this.numberOfGuests = NumberOfGuest.from(numberOfGuests);
    this.occupied = occupied;
  }

  public OrderTable(UUID id, OrderTableName name, NumberOfGuest numberOfGuests, boolean occupied) {
    this.id = id;
    this.name = name;
    this.numberOfGuests = numberOfGuests;
    this.occupied = occupied;
  }

  public void sit() {
    this.occupied = true;
  }

  public void clear() {
    this.occupied = false;
    this.numberOfGuests = NumberOfGuest.EMPTY;
  }

  public void changeNumberOfGuests(NumberOfGuest numberOfGuests) {
    if (!isOccupied()) {
      throw new IllegalStateException();
    }
    this.numberOfGuests = numberOfGuests;
  }

  public boolean isOccupied() {
    return occupied;
  }

  public NumberOfGuest getNumberOfGuests() {
    return numberOfGuests;
  }
}
