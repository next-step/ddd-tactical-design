package kitchenpos.eatinorders.application;

import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrderTable;

public class EatInOrderTableResponse {
  private UUID id;
  private String name;
  private int numberOfGuests;
  private boolean occupied;

  public EatInOrderTableResponse() {}

  public EatInOrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
    this.id = id;
    this.name = name;
    this.numberOfGuests = numberOfGuests;
    this.occupied = occupied;
  }

  public static EatInOrderTableResponse create(final EatInOrderTable eatInOrderTable) {
    return new EatInOrderTableResponse(
        eatInOrderTable.getId(),
        eatInOrderTable.getName(),
        eatInOrderTable.getNumberOfGuests(),
        eatInOrderTable.getOccupied());
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getNumberOfGuests() {
    return numberOfGuests;
  }

  public boolean isOccupied() {
    return occupied;
  }
}
