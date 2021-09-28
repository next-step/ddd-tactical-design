package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, OrderStatus status);
}

