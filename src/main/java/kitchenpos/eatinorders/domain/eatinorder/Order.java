package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.*;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Inheritance
@DiscriminatorColumn(name = "order_type")
public abstract class Order {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  protected OrderType type;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  protected OrderStatus status;

  @Column(name = "order_date_time", nullable = false)
  private LocalDateTime orderDateTime;

  @Embedded
  private OrderLineItems orderLineItems;

  @ManyToOne
  @JoinColumn(
          name = "order_table_id",
          columnDefinition = "binary(16)",
          foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
  )
  private OrderTable orderTable;

  protected Order() {
  }

  protected Order(final OrderType type, final OrderStatus status, final OrderLineItems orderLineItems, final OrderTable orderTable) {
    validate(type, status);
    this.id = UUID.randomUUID();
    this.type = type;
    this.status = status;
    this.orderLineItems = orderLineItems;
    this.orderDateTime = LocalDateTime.now();
    this.orderTable = orderTable;
  }

  protected Order(final OrderType type, final OrderStatus status, final OrderLineItems orderLineItems) {
    this(type, status, orderLineItems, null);
  }

  protected Order(final OrderType type, final OrderLineItems orderLineItems) {
    this(type, OrderStatus.WAITING, orderLineItems, null);
  }

  protected Order(final OrderType type, final OrderLineItems orderLineItems, final OrderTable orderTable) {
    this(type, OrderStatus.WAITING, orderLineItems, orderTable);
  }

  private void validate(final OrderType type, final OrderStatus status) {
    if (Objects.isNull(type)) {
      throw new IllegalArgumentException("주문 타입이 올바르지 않습니다.");
    }

    if (Objects.isNull(status)) {
      throw new IllegalArgumentException("주문 타입이 올바르지 않습니다.");
    }
  }


  public abstract void accept(PassToRiderService acceptOrderService);

  public abstract void delivering();

  public abstract void delivered();

  public void serve() {
    if (status != OrderStatus.ACCEPTED) {
      throw new IllegalStateException(" `주문 상태`가 `접수(ACCEPTED)`이 아닌 주문은 전달할 수 없습니다.");
    }
    status = OrderStatus.SERVED;
  }

  public abstract void complete(ClearOrderTableService clearOrderTableService);

  protected void mapOrder() {
    orderLineItems.setOrder(this);
  }

  protected BigDecimal getOrderLineItemsSum() {
    return orderLineItems.getSum();
  }
  protected void clearOrderTable(){
    orderTable.clear();
  }

  public boolean isDelivery() {
    return getType().equals(OrderType.DELIVERY);
  }

  public boolean isNotEatIn() {
    return !getType().equals(OrderType.EAT_IN);
  }
  public UUID getId() {
    return id;
  }

  protected OrderType getType() {
    return type;
  }

  protected OrderStatus getStatus() {
    return status;
  }

  public UUID getOrderTableId() {
    return orderTable.getId();
  }

  public boolean containsNegativeMenuCounts() {
    return orderLineItems.containsNegativeMenuQuantity();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }



}
