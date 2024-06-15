package kitchenpos.deliveryorders.application;

import java.util.List;

public class DeliveryOrderRequestDto {
  private String deliveryAddress;
  private List<DeliveryOrderLineItemRequestDto> deliveryOrderLineItemRequestDtos;

  public DeliveryOrderRequestDto() {}

  public DeliveryOrderRequestDto(
      String deliveryAddress,
      List<DeliveryOrderLineItemRequestDto> deliveryOrderLineItemRequestDtos) {
    this.deliveryAddress = deliveryAddress;
    this.deliveryOrderLineItemRequestDtos = deliveryOrderLineItemRequestDtos;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public List<DeliveryOrderLineItemRequestDto> getDeliveryOrderLineItemRequestDtos() {
    return deliveryOrderLineItemRequestDtos;
  }
}
