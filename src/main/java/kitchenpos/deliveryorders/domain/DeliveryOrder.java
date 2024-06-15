package kitchenpos.deliveryorders.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

    if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
      throw new IllegalArgumentException();
    }

    this.id = id;
    this.type = DeliveryOrderType.DELIVERY;
    this.status = DeliveryOrderStatus.WAITING;
    this.orderDateTime = new OrderDateTime(LocalDateTime.now());
    this.orderLineItems = new DeliveryOrderLineItems(orderLineItems);
    this.deliveryAddress = new DeliveryAddress(deliveryAddress);
  }

  public static DeliveryOrder createOrder(
      final List<DeliveryOrderLineItem> orderLineItems, final String deliveryAddress) {

    return new DeliveryOrder(UUID.randomUUID(), orderLineItems, deliveryAddress);
  }

  public void accept(final KitchenridersClient kitchenridersClient) {
    if (this.status != DeliveryOrderStatus.WAITING) {
      throw new IllegalStateException();
    }
    final BigDecimal sum = this.orderLineItems.totalPrice();
    kitchenridersClient.requestDelivery(this.id, sum, this.getDeliveryAddress());
  }

  public void serve() {
    if (this.status != DeliveryOrderStatus.ACCEPTED) {
      throw new IllegalStateException();
    }

    this.status = DeliveryOrderStatus.SERVED;
  }

  public void startDelivery() {
    if (this.status != DeliveryOrderStatus.ACCEPTED) {
      throw new IllegalStateException();
    }

    this.status = DeliveryOrderStatus.DELIVERING;
  }

  public void completeDelivery() {
    if (this.status != DeliveryOrderStatus.DELIVERING) {
      throw new IllegalStateException();
    }

    this.status = DeliveryOrderStatus.DELIVERED;
  }

  public void complete() {
    if (this.status != DeliveryOrderStatus.DELIVERED) {
      throw new IllegalStateException();
    }

    this.status = DeliveryOrderStatus.COMPLETED;
  }

  public UUID getId() {
    return id;
  }

  public DeliveryOrderType getType() {
    return type;
  }

  public DeliveryOrderStatus getStatus() {
    return status;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime.getOrderDateTime();
  }

  public List<DeliveryOrderLineItem> getOrderLineItems() {
    return orderLineItems.getOrderLineItems();
  }

  public String getDeliveryAddress() {
    return deliveryAddress.getDeliveryAddress();
  }
}
