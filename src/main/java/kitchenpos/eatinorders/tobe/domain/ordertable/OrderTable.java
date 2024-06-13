package kitchenpos.eatinorders.tobe.domain.ordertable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  @Column(name = "name", nullable = false)
  private OrderTableName name;

  @Embedded
  @Column(name = "number_of_guests", nullable = false)
  private OrderTableNumberOfGuests numberOfGuests;

  @Column(name = "occupied", nullable = false)
  private boolean occupied;

  @Embedded
  private AcceptedOrders orders;

  protected OrderTable() {
  }

  public OrderTable(OrderTableName name) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.numberOfGuests = OrderTableNumberOfGuests.ABSENT;
    this.occupied = false;
  }

  public void sit() {
    this.occupied = true;
  }

  public void clear(boolean throwOnFailure) {
    if(!existsIncompleteOrder()) {
      this.numberOfGuests = OrderTableNumberOfGuests.ABSENT;
      this.occupied = false;
      return;
    }
    if(throwOnFailure) {
      throw new IllegalStateException();
    }
  }

  public void changeNumberOfGuests(OrderTableNumberOfGuests numberOfGuests) {
    if(!this.occupied) {
      throw new IllegalStateException();
    }
    this.numberOfGuests = numberOfGuests;
  }

  private boolean existsIncompleteOrder() {
    return Objects.nonNull(this.orders) && this.orders.existsIncompleteOrder();
  }

  public UUID getId() {
    return id;
  }

  public OrderTableName getName() {
    return name;
  }

  public OrderTableNumberOfGuests getNumberOfGuests() {
    return numberOfGuests;
  }

  public boolean isOccupied() {
    return occupied;
  }

  public AcceptedOrders getOrders() {
    return orders;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderTable that = (OrderTable) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "RegisteredOrderTable{" +
        "id=" + id +
        ", name=" + name +
        ", numberOfGuests=" + numberOfGuests +
        ", occupied=" + occupied +
        ", orders=" + orders +
        '}';
  }
}
