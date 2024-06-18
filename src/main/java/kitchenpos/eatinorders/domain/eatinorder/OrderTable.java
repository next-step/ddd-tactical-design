package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.*;
import kitchenpos.common.domain.orders.OrderTableStatus;

import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
  private static final int ZERO = 0;
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

  private OrderTable(OrderTableName orderTableName, CustomerHeadcount customerHeadCount) {
    this.id = UUID.randomUUID();
    this.name = orderTableName;
    this.customerHeadcount = customerHeadCount;
    this.occupied = OrderTableStatus.UNOCCUPIED;
  }

  private OrderTable(boolean occupied, OrderTableName orderTableName, CustomerHeadcount customerHeadCount) {
    this(orderTableName, customerHeadCount);
    if (occupied) {
      this.occupied = OrderTableStatus.OCCUPIED;
    } else {
      this.occupied = OrderTableStatus.UNOCCUPIED;
    }
  }

  public static OrderTable of(String orderTableName, Integer customerHeadCount) {
    return new OrderTable(OrderTableName.of(orderTableName), CustomerHeadcount.of(customerHeadCount));
  }

  public static OrderTable of(boolean occupied, String orderTableName, Integer customerHeadCount) {
    return new OrderTable(occupied, OrderTableName.of(orderTableName), CustomerHeadcount.of(customerHeadCount));
  }

  public void clear() {
    unoccupy();
  }

  protected void unoccupy() {
    this.occupied = OrderTableStatus.UNOCCUPIED;
    this.customerHeadcount = CustomerHeadcount.zero();
  }

  public void occupy() {
    this.occupied = OrderTableStatus.OCCUPIED;
  }

  public void changeCustomerHeadCounts(Integer headCounts) {
    validateOccupied();
    if (headCounts.compareTo(ZERO) <= ZERO) {
      throw new IllegalArgumentException("방문한 손님 수는 0 이상이어야 한다.");
    }
    this.customerHeadcount = CustomerHeadcount.of(headCounts);
  }

  protected void validateOccupied() {
    if (this.occupied.equals(OrderTableStatus.UNOCCUPIED)) {
      throw new IllegalStateException("사용중이 아닌 주문 테이블의 고객 수를 바꿀 수 없습니다.");
    }
  }

  public UUID getId() {
    return id;
  }

  public Integer getCustomerHeadcount() {
    return customerHeadcount.getHeadCounts();
  }

  public OrderTableStatus getOccupied() {
    return occupied;
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

