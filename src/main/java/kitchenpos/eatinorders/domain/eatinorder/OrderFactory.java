package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.common.domain.ordertables.OrderType;

public interface OrderFactory {
  Order of(final OrderType orderType, final OrderStatus orderStatus, final OrderLineItems orderLineItems, final OrderTable orderTable, final String orderDelivery);

  Order createEatInOrder(final OrderStatus status, final OrderLineItems orderLineItems, final OrderTable orderTable);

  Order createDeliveryOrder(final OrderStatus status, final OrderLineItems orderLineItems, final String deliveryAddress);

  Order createTakeoutOrder(final OrderStatus status, final OrderLineItems orderLineItems);
}
