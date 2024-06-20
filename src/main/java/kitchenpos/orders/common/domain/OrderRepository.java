package kitchenpos.orders.common.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.orders.store.domain.OrderTable;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

