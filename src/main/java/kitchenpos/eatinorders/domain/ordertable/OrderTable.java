package kitchenpos.eatinorders.domain.ordertable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "number_of_guests", nullable = false)
  private int numberOfGuests;

  @Column(name = "occupied", nullable = false)
  private boolean occupied;

  protected OrderTable() {
  }

}

