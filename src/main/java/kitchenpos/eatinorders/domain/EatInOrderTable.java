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

  protected EatInOrderTable(
      final UUID id, final String name, final int numberOfGuests, final boolean occupied) {
    this.id = id;
    this.name = new Name(name);
    this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    this.occupied = new Occupied(occupied);
  }

  public static EatInOrderTable createOrderTable(
      final String name, final int numberOfGuests, final boolean occupied) {
    return new EatInOrderTable(UUID.randomUUID(), name, numberOfGuests, occupied);
  }

  public void sit(int numberOfGuests) {
    if (this.occupied.isOccupied()) {
      throw new IllegalStateException();
    }

    if (numberOfGuests < 0) {
      throw new IllegalArgumentException();
    }

    this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    this.occupied = new Occupied(true);
  }

  public void changeNumberOfGuests(final int numberOfGuests) {
    if (!this.occupied.isOccupied()) {
      throw new IllegalStateException();
    }

    if (numberOfGuests < 0) {
      throw new IllegalArgumentException();
    }

    this.numberOfGuests = new NumberOfGuests(numberOfGuests);
  }

  public void clear() {
    if (!this.occupied.isOccupied()) {
      throw new IllegalStateException();
    }

    if (this.numberOfGuests.getNumberOfGuests() < 0) {
      throw new IllegalArgumentException();
    }

    this.numberOfGuests = new NumberOfGuests(0);
    this.occupied = new Occupied(false);
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
