package kitchenpos.orders.ordertables.domain;

import kitchenpos.orders.eatinorders.domain.EatInOrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderTableRepository {
    OrderTable save(OrderTable orderTable);

    Optional<OrderTable> findById(OrderTableId id);

    List<OrderTable> findAll();

    boolean existsByOrderAndStatusNot(OrderTableId orderTableId, EatInOrderStatus status);


}

