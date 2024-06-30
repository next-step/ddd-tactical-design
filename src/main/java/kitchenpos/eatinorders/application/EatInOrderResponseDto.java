package kitchenpos.eatinorders.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.domain.EatInOrderType;

public class EatInOrderResponseDto {
  private UUID orderId;
  private EatInOrderType type;
  private EatInOrderStatus status;
  private LocalDateTime orderDateTime;
  private List<OrderLineItemResponseDto> orderLineItemResponseDtos;
  private UUID orderTableId;

  public EatInOrderResponseDto(
      UUID orderId,
      EatInOrderType type,
      EatInOrderStatus status,
      LocalDateTime orderDateTime,
      List<OrderLineItemResponseDto> orderLineItemResponseDtos,
      UUID orderTableId) {
    this.orderId = orderId;
    this.type = type;
    this.status = status;
    this.orderDateTime = orderDateTime;
    this.orderLineItemResponseDtos = orderLineItemResponseDtos;
    this.orderTableId = orderTableId;
  }

  public static EatInOrderResponseDto create(final EatInOrder eatInOrder) {
    return new EatInOrderResponseDto(
        eatInOrder.getId(),
        eatInOrder.getType(),
        eatInOrder.getStatus(),
        eatInOrder.getOrderDateTime(),
        OrderLineItemResponseDto.create(eatInOrder.getOrderLineItems()),
        eatInOrder.getOrderTable().getId());
  }

  public UUID getOrderId() {
    return orderId;
  }

  public EatInOrderType getType() {
    return type;
  }

  public EatInOrderStatus getStatus() {
    return status;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }

  public List<OrderLineItemResponseDto> getOrderLineItemResponseDtos() {
    return orderLineItemResponseDtos;
  }

  public UUID getOrderTableId() {
    return orderTableId;
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
        List<EatInOrderLineItem> eatInOrderLineItems) {
      return eatInOrderLineItems.stream()
          .map(o -> new OrderLineItemResponseDto(o.getQuantity(), o.getMenuId()))
          .toList();
    }
  }
}
