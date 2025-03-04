package kitchenpos.order.common.domain.service;

import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.common.domain.model.OrderVo.Create;

public interface OrderCommandService {
    OrderVo.OrderInfo create(Create request);
    OrderVo.OrderInfo accept(final OrderId orderId);
    OrderVo.OrderInfo serve(final OrderId orderId);
    OrderVo.OrderInfo complete(final OrderId orderId);

}
