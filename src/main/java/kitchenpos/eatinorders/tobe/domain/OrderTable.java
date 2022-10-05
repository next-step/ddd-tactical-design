package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class OrderTable {

  public static final int EMPTY_GUEST = 0;

  private final UUID id;
  private final OrderTableName name;
  private int numberOfGuests;
  private boolean occupied;

  public OrderTable(UUID id, String name) {
    this(id, OrderTableName.from(name), EMPTY_GUEST, false);
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
    this.numberOfGuests = numberOfGuests;
  }

  public void sit() {
    this.occupied = true;
  }

  public void clear() {
    this.occupied = false;
    this.numberOfGuests = EMPTY_GUEST;
  }

  public boolean isOccupied() {
    return occupied;
  }

  public int getNumberOfGuests() {
    return numberOfGuests;
  }
}
