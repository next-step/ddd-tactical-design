package kitchenpos.order.common.repository;

import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderStatus;
import kitchenpos.order.eatinorder.domain.model.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

