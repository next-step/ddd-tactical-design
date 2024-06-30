package kitchenpos.eatinorders.application;

import java.util.List;
import java.util.UUID;

public class EatInOrderRequestDto {
  private UUID orderTableId;
  private List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDtos;

  public EatInOrderRequestDto() {}

  public EatInOrderRequestDto(
      UUID orderTableId, List<EatInOrderLineItemRequestDto> eatInOrderLineItemRequestDtos) {
    this.orderTableId = orderTableId;
    this.eatInOrderLineItemRequestDtos = eatInOrderLineItemRequestDtos;
  }

  public List<EatInOrderLineItemRequestDto> getEatInOrderLineItemRequestDtos() {
    return eatInOrderLineItemRequestDtos;
  }

  public UUID getOrderTableId() {
    return orderTableId;
  }
}
