package kitchenpos.eatinorders.tobe.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "orders")
@Entity
public class Order {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "order_date_time", nullable = false)
  private LocalDateTime orderDateTime;

  @Column(name = "order_table_id", nullable = false)
  private UUID orderTableId;

  @Embedded
  private OrderLineItems orderLineItems;

  protected Order() {

  }

  public Order(UUID id, UUID orderTableId, List<OrderLineItem> orderLineItems) {
    this(id, orderTableId, OrderStatus.WAITING, new OrderLineItems(orderLineItems));
  }

  public Order(UUID id, UUID orderTableId, OrderStatus status, List<OrderLineItem> orderLineItems) {
    this(id, orderTableId, status, new OrderLineItems(orderLineItems));
  }

  public Order(UUID id, UUID orderTableId, OrderStatus status, OrderLineItems orderLineItems) {
    this.id = id;
    this.status = status;
    this.orderDateTime = LocalDateTime.now();
    this.orderTableId = orderTableId;
    this.orderLineItems = orderLineItems;
  }

  public void place(OrderValidator validator) {
    validator.validate(this);
  }

  public void accept() {
    if (!status.isWaiting()) {
      throw new IllegalStateException();
    }
    this.status = OrderStatus.ACCEPTED;
  }

  public void serve() {
    if (!status.isAccepted()) {
      throw new IllegalStateException();
    }
    this.status = OrderStatus.SERVED;
  }

  public void complete(OrderTableCleaner orderTableCleaner) {
    if (!status.isServed()) {
      throw new IllegalStateException();
    }
    this.status = OrderStatus.COMPLETED;
    orderTableCleaner.clear(this.orderTableId);
  }

  public List<OrderLineItem> getOrderLineItems() {
    return orderLineItems.getOrderLineItems();
  }

  public UUID getId() {
    return this.id;
  }

  public UUID getOrderTableId() {
    return this.orderTableId;
  }

  public OrderStatus getStatus() {
    return this.status;
  }
}
