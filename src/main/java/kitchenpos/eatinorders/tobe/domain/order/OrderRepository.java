package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.Optional;
import kitchenpos.common.domain.OrderId;

public interface OrderRepository {

    Order save(final Order order);

    Optional<Order> findById(final OrderId id);

    List<Order> findAll();

    // boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final OrderStatus status);
}
