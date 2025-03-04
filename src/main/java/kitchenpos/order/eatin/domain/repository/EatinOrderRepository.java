package kitchenpos.order.eatin.domain.repository;

import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.eatin.domain.model.OrderTableId;

public interface EatinOrderRepository {
    boolean existsByOrderTableIdAndStatusNot(OrderTableId orderTableId, OrderStatus status);
}

