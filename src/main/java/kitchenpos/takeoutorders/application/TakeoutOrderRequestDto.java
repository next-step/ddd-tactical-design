package kitchenpos.takeoutorders.application;

import java.util.List;

public class TakeoutOrderRequestDto {
  private List<TakeoutOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos;

  public TakeoutOrderRequestDto() {}

  public TakeoutOrderRequestDto(
      List<TakeoutOrderLineItemRequestDto> takeoutOrderLineItemRequestDtos) {
    this.takeoutOrderLineItemRequestDtos = takeoutOrderLineItemRequestDtos;
  }

  public List<TakeoutOrderLineItemRequestDto> getTakeoutOrderLineItemRequestDtos() {
    return takeoutOrderLineItemRequestDtos;
  }
}
