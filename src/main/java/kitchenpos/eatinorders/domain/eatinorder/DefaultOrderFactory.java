package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrderFactory implements OrderFactory {

  private final KitchenridersClient kitchenridersClient;

  public DefaultOrderFactory(KitchenridersClient kitchenridersClient) {
    this.kitchenridersClient = kitchenridersClient;
  }

  public Order of(final OrderType orderType, final OrderStatus orderStatus, final OrderLineItems orderLineItems, final OrderTable orderTable, final String orderDelivery) {
    if (orderType.equals(OrderType.TAKEOUT)) {
      return createTakeoutOrder(orderStatus, orderLineItems);
    } else if (orderType.equals(OrderType.EAT_IN)) {
      return createEatInOrder(orderStatus, orderLineItems, orderTable);
    } else if (orderType.equals(OrderType.DELIVERY)) {
      return createDeliveryOrder(orderStatus, orderLineItems, orderDelivery);
    }

    throw new IllegalStateException("지원하지 않는 주문타입입니다.");
  }

  public Order createEatInOrder(final OrderStatus status, final OrderLineItems orderLineItems, final OrderTable orderTable) {
    Order order = new EatInOrder(status, orderLineItems, orderTable);
    order.mapOrder();
    return order;
  }

  public Order createDeliveryOrder(final OrderStatus status, final OrderLineItems orderLineItems, String deliveryAddress) {
    Order order = new DeliveryOrder(status, orderLineItems, deliveryAddress);
    order.mapOrder();
    return order;
  }

  public Order createTakeoutOrder(final OrderStatus status, final OrderLineItems orderLineItems) {
    Order order = new TakeoutOrder(status, orderLineItems);
    order.mapOrder();
    return order;
  }

}
