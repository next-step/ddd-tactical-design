package kitchenpos.takeoutorders.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class TakeoutOrder {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private TakeoutOrderType type;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private TakeoutOrderStatus status;

  @Embedded private OrderDateTime orderDateTime;

  @Embedded private TakeoutOrderLineItems orderLineItems;

  protected TakeoutOrder() {}

  protected TakeoutOrder(final UUID id, final List<TakeoutOrderLineItem> orderLineItems) {
    this.id = id;
    this.type = TakeoutOrderType.TAKEOUT;
    this.status = TakeoutOrderStatus.WAITING;
    this.orderDateTime = new OrderDateTime(LocalDateTime.now());
    this.orderLineItems = new TakeoutOrderLineItems(orderLineItems);
  }

  public static TakeoutOrder createOrder(final List<TakeoutOrderLineItem> orderLineItems) {
    return new TakeoutOrder(UUID.randomUUID(), orderLineItems);
  }

  public void accepted() {
    if (this.status != TakeoutOrderStatus.WAITING) {
      throw new IllegalStateException();
    }

    this.status = TakeoutOrderStatus.ACCEPTED;
  }

  public void serve() {
    if (this.status != TakeoutOrderStatus.ACCEPTED) {
      throw new IllegalStateException();
    }

    this.status = TakeoutOrderStatus.SERVED;
  }

  public void completed() {
    if (this.status != TakeoutOrderStatus.SERVED) {
      throw new IllegalStateException();
    }

    this.status = TakeoutOrderStatus.COMPLETED;
  }

  public UUID getId() {
    return id;
  }

  public TakeoutOrderType getType() {
    return type;
  }

  public TakeoutOrderStatus getStatus() {
    return status;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime.getOrderDateTime();
  }

  public List<TakeoutOrderLineItem> getOrderLineItems() {
    return orderLineItems.getOrderLineItems();
  }
}
