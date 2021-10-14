package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeOrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(UUID id);

    List<OrderTable> findAll();
}

