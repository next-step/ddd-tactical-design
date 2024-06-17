package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.annotation.DomainService;

@DomainService
public class AcceptOrderService {
  private final KitchenridersClient kitchenridersClient;

  public AcceptOrderService(KitchenridersClient kitchenridersClient) {
    this.kitchenridersClient = kitchenridersClient;
  }

  public void acceptOrder(Order order) {
    if (order.isDelivery()) {
      order.accept(kitchenridersClient);
    } else {
      order.accept();
    }
  }
}
