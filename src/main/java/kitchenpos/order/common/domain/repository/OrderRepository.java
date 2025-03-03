package kitchenpos.order.common.domain.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.eatin.domain.model.OrderTableId;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(OrderId id);

    List<Order> findAll();

    boolean existsByOrderTableIdAndStatusNot(OrderTableId orderTableId, OrderStatus status);

}

