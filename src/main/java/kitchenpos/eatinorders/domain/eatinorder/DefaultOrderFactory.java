package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.annotation.FactoryService;
import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;

@FactoryService
public class DefaultOrderFactory implements OrderFactory {

  public Order of(final OrderType orderType, final OrderStatus orderStatus, final OrderLineItems orderLineItems, final OrderTable orderTable, final String orderDelivery) {
    if (OrderType.TAKEOUT.equals(orderType)) {
      return createTakeoutOrder(orderStatus, orderLineItems);
    } else if (OrderType.EAT_IN.equals(orderType)) {
      return createEatInOrder(orderStatus, orderLineItems, orderTable);
    } else if (OrderType.DELIVERY.equals(orderType)) {
      return createDeliveryOrder(orderStatus, orderLineItems, orderDelivery);
    }

    throw new IllegalArgumentException("지원하지 않는 주문타입입니다.");
  }

  public Order createEatInOrder(final OrderStatus status, final OrderLineItems orderLineItems, final OrderTable orderTable) {
    Order order = new EatInOrder(status, orderLineItems, orderTable);
    order.mapOrder();
    return order;
  }

  public Order createDeliveryOrder(final OrderStatus status, final OrderLineItems orderLineItems, final String deliveryAddress) {
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
