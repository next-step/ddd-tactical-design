package kitchenpos.order.eatin.domain.repository;

import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.eatin.domain.entity.OrderTable;

public interface EatinOrderRepository {
    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

