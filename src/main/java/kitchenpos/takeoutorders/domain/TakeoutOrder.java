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
}
