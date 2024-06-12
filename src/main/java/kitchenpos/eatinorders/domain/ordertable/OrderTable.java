package kitchenpos.eatinorders.domain.ordertable;

import jakarta.persistence.*;
import kitchenpos.common.domain.orders.OrderTableStatus;

import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "name", nullable = false)
  @Embedded
  private OrderTableName name;

  @Column(name = "customer_headcounts", nullable = false)
  private CustomerHeadcount customerHeadcount;

  @Column(name = "occupied", nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderTableStatus occupied;

  protected OrderTable() {
  }

  private OrderTable(OrderTableName orderTableName, CustomerHeadcount customerHeadCount, OrderTableStatus orderTableStatus) {
    this.id = UUID.randomUUID();
    this.name = orderTableName;
    this.customerHeadcount = customerHeadCount;
    this.occupied = OrderTableStatus.UNOCCUPIED;
  }

  public static OrderTable of(String orderTableName, Long customerHeadCount, OrderTableStatus orderTableStatus){
      return new OrderTable(OrderTableName.of(orderTableName), CustomerHeadcount.of(customerHeadCount), orderTableStatus);
  }

  protected void clear(){
    validateUnoccupied();

    unoccupy();
  }
  protected void unoccupy() {
    this.occupied = OrderTableStatus.UNOCCUPIED;
    this.customerHeadcount = CustomerHeadcount.zero();
  }

  protected void occupy(Long headCounts) {
    this.occupied = OrderTableStatus.OCCUPIED;
    changeCustomerHeadCounts(headCounts);
  }

  protected void changeCustomerHeadCounts(Long headCounts) {
    validateOccupied();

    this.customerHeadcount = CustomerHeadcount.of(headCounts);
  }

  private void validateOccupied(){
    if (this.occupied.equals(OrderTableStatus.UNOCCUPIED)){
      throw new IllegalStateException("빈 테이블은 방문한 손님 수를 변경할 수 없다.");
    }
  }

  private void validateUnoccupied(){
    if (this.occupied.equals(OrderTableStatus.OCCUPIED)){
      throw new IllegalStateException("완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.");
    }
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

