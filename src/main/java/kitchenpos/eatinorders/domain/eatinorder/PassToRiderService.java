package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.annotation.DomainService;

@DomainService
public class PassToRiderService {
  private final KitchenridersClient kitchenridersClient;

  public PassToRiderService(KitchenridersClient kitchenridersClient) {
    this.kitchenridersClient = kitchenridersClient;
  }

  public void acceptOrder(Order order) {
    if (order.isDelivery()) {
      DeliveryOrder deliveryOrder = (DeliveryOrder) order;
      kitchenridersClient.requestDelivery(deliveryOrder.getId(), deliveryOrder.getOrderLineItemsSum(), deliveryOrder.getDeliveryAddress());
    }
  }
}
