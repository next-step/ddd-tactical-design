package kitchenpos.order.delivery.domain.service;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;

public interface DeliveryService {
    OrderVo.OrderInfo startDelivery(final OrderId orderId);
    OrderVo.OrderInfo completeDelivery(final OrderId orderId);
    void requestDelivery(final OrderId orderId, OrderLineItems items);
    DeliveryOrder createDeliveryOrder(OrderId orderId, OrderVo.Create request, OrderLineItems orderLineItems);
}
