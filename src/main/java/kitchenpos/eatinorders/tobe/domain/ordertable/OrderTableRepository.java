package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.List;
import java.util.Optional;
import kitchenpos.common.domain.OrderTableId;

public interface OrderTableRepository {

    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(OrderTableId id);

    List<OrderTable> findAll();
}
