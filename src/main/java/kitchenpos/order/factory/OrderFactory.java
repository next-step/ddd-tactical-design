package kitchenpos.order.factory;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderLineItems;
import kitchenpos.order.domain.OrderTable;

public interface OrderFactory {
    String INVALID_ORDER_TYPE_ERROR = "주문 유형이 유효하지 않습니다.";

    Order createOrder(OrderLineItems orderLineItems, OrderTable orderTable, String deliveryAddress);
}
