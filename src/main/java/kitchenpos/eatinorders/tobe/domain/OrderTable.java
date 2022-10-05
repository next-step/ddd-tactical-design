package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;

public class OrderTable {

  private final UUID id;
  private final OrderTableName name;
  private final int numberOfGuests;

  public OrderTable(UUID id, String name, int numberOfGuests) {
    this.id = id;
    this.name = OrderTableName.from(name);
    this.numberOfGuests = numberOfGuests;
  }

  public OrderTable(UUID id, OrderTableName name, int numberOfGuests) {
    this.id = id;
    this.name = name;
    this.numberOfGuests = numberOfGuests;
  }
}
