package kitchenpos.deliveryorders.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.deliveryorders.domain.DeliveryOrder;
import kitchenpos.deliveryorders.domain.DeliveryOrderLineItem;
import kitchenpos.deliveryorders.domain.DeliveryOrderStatus;
import kitchenpos.deliveryorders.domain.DeliveryOrderType;

public class DeliveryOrderResponseDto {
  private UUID orderId;
  private DeliveryOrderType type;
  private DeliveryOrderStatus status;
  private LocalDateTime orderDateTime;
  private List<OrderLineItemResponseDto> orderLineItemResponseDtos;

  public DeliveryOrderResponseDto(
      UUID orderId,
      DeliveryOrderType type,
      DeliveryOrderStatus status,
      LocalDateTime orderDateTime,
      List<OrderLineItemResponseDto> orderLineItemResponseDtos) {
    this.orderId = orderId;
    this.type = type;
    this.status = status;
    this.orderDateTime = orderDateTime;
    this.orderLineItemResponseDtos = orderLineItemResponseDtos;
  }

  public static DeliveryOrderResponseDto create(final DeliveryOrder deliveryOrder) {
    return new DeliveryOrderResponseDto(
        deliveryOrder.getId(),
        deliveryOrder.getType(),
        deliveryOrder.getStatus(),
        deliveryOrder.getOrderDateTime(),
        OrderLineItemResponseDto.create(deliveryOrder.getOrderLineItems()));
  }

  public UUID getOrderId() {
    return orderId;
  }

  public DeliveryOrderType getType() {
    return type;
  }

  public DeliveryOrderStatus getStatus() {
    return status;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }

  public List<OrderLineItemResponseDto> getOrderLineItemResponseDtos() {
    return orderLineItemResponseDtos;
  }

  static class OrderLineItemResponseDto {
    private long quantity;
    private UUID menuId;

    public OrderLineItemResponseDto() {}

    public OrderLineItemResponseDto(long quantity, UUID menuId) {
      this.quantity = quantity;
      this.menuId = menuId;
    }

    public static List<OrderLineItemResponseDto> create(
        List<DeliveryOrderLineItem> deliveryOrderLineItems) {
      return deliveryOrderLineItems.stream()
          .map(o -> new OrderLineItemResponseDto(o.getQuantity(), o.getMenuId()))
          .toList();
    }
  }
}
