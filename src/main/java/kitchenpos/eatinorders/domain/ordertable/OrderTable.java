package kitchenpos.eatinorders.domain.ordertable;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.domain.orders.OrderTableStatus;

@Table(name = "order_table")
@Entity
public class OrderTable {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "name", nullable = false)
  @Embedded
  private OrderTableStatus name;

  @Column(name = "customer_headcounts", nullable = false)
  private CustomerHeadcount customerHeadcount;

  @Column(name = "occupied", nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderTableStatus occupied;

  protected OrderTable() {
  }

  protected void unoccupy(){
    this.occupied = OrderTableStatus.UNOCCUPIED;
  }

  protected void occupy(){
    this.occupied = OrderTableStatus.OCCUPIED;
  }

  protected void changeCustomerHeadCounts(Long headCounts){
    validate(headCounts);

    this.customerHeadcount = CustomerHeadcount.of(headCounts);

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
}

