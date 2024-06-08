package kitchenpos.eatinorders.domain.eatinorder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(UUID id);

    List<Order> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}

