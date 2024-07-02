package kitchenpos.orders.store.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.orders.store.domain.tobe.OrderTable;

public interface OrderTableRepository {

    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(UUID id);

    List<OrderTable> findAll();
}

