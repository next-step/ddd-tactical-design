package kitchenpos.eatinorders.domain.order;

import kitchenpos.ordertables.domain.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(EatInOrderId id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTableId orderTableId, EatInOrderStatus status);
}

