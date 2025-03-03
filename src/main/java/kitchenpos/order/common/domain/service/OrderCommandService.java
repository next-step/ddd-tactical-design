package kitchenpos.order.common.domain.service;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderVo;

public interface OrderCommandService {
//    OrderResponse.GetOrder create(final Order request);
    OrderVo.OrderInfo accept(final OrderId orderId);
    OrderVo.OrderInfo serve(final OrderId orderId);
    OrderVo.OrderInfo complete(final OrderId orderId);
}
