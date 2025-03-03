package kitchenpos.order.common.domain.repository;

import java.util.List;
import java.util.Optional;
import kitchenpos.order.eatin.domain.entity.OrderTable;
import kitchenpos.order.eatin.domain.model.OrderTableId;

public interface OrderTableRepository {

    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(OrderTableId id);

    List<OrderTable> findAll();
}

