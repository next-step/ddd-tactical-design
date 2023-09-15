package kitchenpos.ordertables.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(OrderTableId id);

    List<OrderTable> findAll();
}

