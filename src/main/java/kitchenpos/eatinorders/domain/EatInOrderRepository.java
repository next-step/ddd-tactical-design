package kitchenpos.eatinorders.domain;

import java.util.List;
import java.util.Optional;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(EatInOrderId id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTableId orderTableId, EatInOrderStatus status);
}

