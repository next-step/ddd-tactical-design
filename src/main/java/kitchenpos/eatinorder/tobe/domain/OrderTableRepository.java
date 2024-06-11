package kitchenpos.eatinorder.tobe.domain;

import kitchenpos.eatinorder.tobe.domain.OrderTable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    kitchenpos.eatinorder.domain.OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(UUID id);

    List<OrderTable> findAll();
}

