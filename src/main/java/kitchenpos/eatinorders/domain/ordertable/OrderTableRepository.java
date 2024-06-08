package kitchenpos.eatinorders.domain.ordertable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;

public interface OrderTableRepository {
    OrderTableRequest save(OrderTableRequest orderTableRequest);

    Optional<OrderTableRequest> findById(UUID id);

    List<OrderTableRequest> findAll();
}

