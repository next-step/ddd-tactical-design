package kitchenpos.eatinorders.tobe.domain.order.createsupporter;

import java.util.Objects;
import java.util.UUID;

public class OrderTable {

  private final UUID id;
  private final boolean occupied;

  public OrderTable(UUID id, boolean occupied) {
    this.id = Objects.requireNonNull(id);
    this.occupied = occupied;
  }

  public UUID getId() {
    return id;
  }

  public boolean isOccupied() {
    return occupied;
  }
}
