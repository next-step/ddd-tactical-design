package kitchenpos.order.delivery.domain.service;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderVo;

public interface DeliveryService {
    OrderVo.OrderInfo startDelivery(final OrderId orderId);
    OrderVo.OrderInfo completeDelivery(final OrderId orderId);
}
