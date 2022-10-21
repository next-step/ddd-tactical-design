package kitchenpos.eatinorders.feedback.domain;

import java.util.List;
import java.util.Optional;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(Long id);

    List<OrderTable> findAll();
}
