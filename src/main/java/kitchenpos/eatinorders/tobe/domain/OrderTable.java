package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class OrderTable {

  private final UUID id;
  private final String name;
  private final int numberOfGuests;

  public OrderTable(UUID id, String name, int numberOfGuests) {
    this.id = id;
    this.name = name;
    this.numberOfGuests = numberOfGuests;
  }
}
