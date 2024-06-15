package kitchenpos.eatinorders.domain;

import jakarta.persistence.*;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class EatInOrderTable {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded private Name name;

  @Embedded private NumberOfGuests numberOfGuests;

  @Embedded private Occupied occupied;

  protected EatInOrderTable() {}

  protected EatInOrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
    this.id = id;
    this.name = new Name(name);
    this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    this.occupied = new Occupied(occupied);
  }

  public static EatInOrderTable createOrderTable(
      String name, int numberOfGuests, boolean occupied) {
    return new EatInOrderTable(UUID.randomUUID(), name, numberOfGuests, occupied);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name.getName();
  }

  public int getNumberOfGuests() {
    return numberOfGuests.getNumberOfGuests();
  }

  public boolean getOccupied() {
    return occupied.isOccupied();
  }
}
