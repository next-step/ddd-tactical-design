package kitchenpos.takeoutorders.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.takeoutorders.domain.TakeoutOrder;
import kitchenpos.takeoutorders.domain.TakeoutOrderLineItem;
import kitchenpos.takeoutorders.domain.TakeoutOrderStatus;
import kitchenpos.takeoutorders.domain.TakeoutOrderType;

public class TakeoutOrderResponseDto {
  private UUID orderId;
  private TakeoutOrderType type;
  private TakeoutOrderStatus status;
  private LocalDateTime orderDateTime;
  private List<OrderLineItemResponseDto> orderLineItemResponseDtos;

  public TakeoutOrderResponseDto(
      UUID orderId,
      TakeoutOrderType type,
      TakeoutOrderStatus status,
      LocalDateTime orderDateTime,
      List<OrderLineItemResponseDto> orderLineItemResponseDtos) {
    this.orderId = orderId;
    this.type = type;
    this.status = status;
    this.orderDateTime = orderDateTime;
    this.orderLineItemResponseDtos = orderLineItemResponseDtos;
  }

  public static TakeoutOrderResponseDto create(final TakeoutOrder takeoutOrder) {
    return new TakeoutOrderResponseDto(
        takeoutOrder.getId(),
        takeoutOrder.getType(),
        takeoutOrder.getStatus(),
        takeoutOrder.getOrderDateTime(),
        OrderLineItemResponseDto.create(takeoutOrder.getOrderLineItems()));
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
        List<TakeoutOrderLineItem> takeoutOrderLineItems) {
      return takeoutOrderLineItems.stream()
          .map(o -> new OrderLineItemResponseDto(o.getQuantity(), o.getMenuId()))
          .toList();
    }
  }
}
