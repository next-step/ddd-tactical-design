package kitchenpos.eatinorders.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_table")
@Entity
public class OrderTable {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  private OrderTableName name;

  @Embedded
  private NumberOfGuest numberOfGuests;

  @Column(name = "occupied", nullable = false)
  private boolean occupied;

  protected OrderTable() {

  }

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
    this(id, name, NumberOfGuest.from(numberOfGuests), occupied);
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

  public UUID getId() {
    return id;
  }
}
