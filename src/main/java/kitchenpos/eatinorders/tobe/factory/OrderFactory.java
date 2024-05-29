package kitchenpos.eatinorders.tobe.factory;

import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderTable;

public interface OrderFactory {
    String INVALID_ORDER_TYPE_ERROR = "주문 유형이 유효하지 않습니다.";

    Order createOrder(OrderLineItems orderLineItems, OrderTable orderTable, String deliveryAddress);
}
