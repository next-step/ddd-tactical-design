package kitchenpos.eatinorders.tobe.repository;

import kitchenpos.eatinorders.tobe.entity.OrderTable;

import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {

    void save(OrderTable orderTable);

    Optional<OrderTable> findBy(UUID id);
}
