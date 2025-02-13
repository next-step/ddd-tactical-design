package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.Order;
import kitchenpos.eatinorder.domain.model.OrderStatus;
import kitchenpos.eatinorder.domain.model.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

