package kitchenpos.deliveryorders.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class DeliveryOrder {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private DeliveryOrderType type;

  @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
  @Enumerated(EnumType.STRING)
  private DeliveryOrderStatus status;

  @Embedded private OrderDateTime orderDateTime;

  @Embedded private DeliveryOrderLineItems orderLineItems;

  @Embedded private DeliveryAddress deliveryAddress;

  protected DeliveryOrder() {}

  protected DeliveryOrder(
      final UUID id,
      final List<DeliveryOrderLineItem> orderLineItems,
      final String deliveryAddress) {
    this.id = id;
    this.type = DeliveryOrderType.DELIVERY;
    this.status = DeliveryOrderStatus.WAITING;
    this.orderDateTime = new OrderDateTime(LocalDateTime.now());
    this.orderLineItems = new DeliveryOrderLineItems(orderLineItems);
    this.deliveryAddress = new DeliveryAddress(deliveryAddress);
  }

  public void create() {}

  public void accept() {}

  public void serve() {}

  public void startDelivery() {}

  public void completeDelivery() {}

  public void complete() {}
}
