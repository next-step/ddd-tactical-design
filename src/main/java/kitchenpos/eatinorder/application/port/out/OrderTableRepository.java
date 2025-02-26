package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.OrderTableEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    OrderTableEntity save(OrderTableEntity orderTableEntity);

    Optional<OrderTableEntity> findById(UUID id);

    List<OrderTableEntity> findAll();
}

