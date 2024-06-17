package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus orderStatus);
}

