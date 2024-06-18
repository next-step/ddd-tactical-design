package kitchenpos.eatinorders.domain.eatinorder;

import kitchenpos.common.domain.orders.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(UUID id);

    List<OrderTable> findAll();
}

