package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {

    OrderTable save(final OrderTable orderTable);

    Optional<OrderTable> findById(final UUID id);
}
