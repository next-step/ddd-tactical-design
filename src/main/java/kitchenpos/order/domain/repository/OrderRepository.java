package kitchenpos.order.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.domain.entity.Order;
import kitchenpos.order.domain.entity.OrderStatus;
import kitchenpos.order.domain.entity.OrderTable;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

