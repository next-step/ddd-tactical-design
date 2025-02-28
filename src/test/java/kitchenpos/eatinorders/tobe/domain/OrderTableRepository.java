package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.OrderTableId;

import java.util.List;
import java.util.Optional;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(OrderTableId id);

    List<OrderTable> findAll();
}

