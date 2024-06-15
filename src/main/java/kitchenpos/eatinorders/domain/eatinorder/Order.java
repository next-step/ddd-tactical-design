package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;

@Table(name = "orders")
@Entity
public class Order {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private OrderType type;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "order_date_time", nullable = false)
  private LocalDateTime orderDateTime;

  @Embedded
  private OrderLineItems orderLineItems;

  @Column(name = "delivery_address")
  private String deliveryAddress;

  @ManyToOne
  @JoinColumn(
      name = "order_table_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_orders_to_order_table")
  )
  private OrderTable orderTable;

  protected Order() {
  }

  private Order(final OrderType type, final OrderStatus status, final OrderLineItems orderLineItems, final OrderTable orderTable){
    this.id = UUID.randomUUID();
    this.type = type;
    this.status = status;
    this.orderLineItems = orderLineItems;
    this.orderDateTime = LocalDateTime.now();
    this.orderTable = orderTable;
  }

  private Order(final OrderType type, final OrderStatus status, final OrderLineItems orderLineItems){
    this(type, status, orderLineItems, null);
  }
  private Order(final OrderType type, final OrderLineItems orderLineItems){
    this(type, OrderStatus.WAITING, orderLineItems, null);
  }
  private Order(final OrderType type, final OrderLineItems orderLineItems, final OrderTable orderTable){
    this(type, OrderStatus.WAITING, orderLineItems, orderTable);
  }
  public static Order of(final OrderType type, final OrderStatus status, final OrderLineItems orderLineItems, final OrderTable orderTable){
    Order order = new Order(type, status, orderLineItems, orderTable);
    order.mapOrder();
    return order;
  }
  public static Order of(final OrderType type, final OrderLineItems orderLineItems, final OrderTable orderTable){
    Order order = new Order(type, orderLineItems, orderTable);
    order.mapOrder();
    return order;
  }
  public void accept(){
    if (status != OrderStatus.WAITING){
      throw new RuntimeException(" `주문 상태`가 `대기중(WAITING)`이 아닌 주문은 수락할 수 없습니다.");
    }
    status = OrderStatus.SERVED;
  }

  public void serve(){
    if (status != OrderStatus.ACCEPTED){
      throw new RuntimeException(" `주문 상태`가 `접수(ACCEPTED)`이 아닌 주문은 전달할 수 없습니다.");
    }
    status = OrderStatus.SERVED;
  }

  public void complete(){
    if (status != OrderStatus.SERVED){
      throw new RuntimeException(" `주문 상태`가 `전달(SERVED)`이 아닌 주문은 완료할 수 없습니다.");
    }
    status = OrderStatus.COMPLETED;
  }
  private void mapOrder(){
    orderLineItems.setOrder(this);
  }

  public UUID getId() {
    return id;
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
