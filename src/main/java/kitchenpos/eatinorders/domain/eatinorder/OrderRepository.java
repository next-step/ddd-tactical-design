package kitchenpos.eatinorders.domain.eatinorder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.common.domain.OrderStatus;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;

public interface OrderRepository {
    OrderRequest save(OrderRequest orderRequest);

    Optional<OrderRequest> findById(UUID id);

    List<OrderRequest> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTableRequest orderTableRequest, OrderStatus status);
}

