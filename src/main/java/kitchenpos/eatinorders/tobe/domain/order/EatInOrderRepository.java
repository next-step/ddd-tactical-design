package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    EatInOrder save(final EatInOrder order);

    Optional<EatInOrder> findById(final UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(final EatInOrderTable orderTable, final EatInOrderStatus status);
}
