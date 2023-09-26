package kitchenpos.eatinorders.order.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import kitchenpos.eatinorders.ordertable.domain.OrderTable;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}
