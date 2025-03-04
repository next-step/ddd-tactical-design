package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    OrderEntity save(OrderEntity order);

    Optional<OrderEntity> findById(OrderId id);

    List<OrderEntity> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTableId orderTableId, OrderStatus status);
}
