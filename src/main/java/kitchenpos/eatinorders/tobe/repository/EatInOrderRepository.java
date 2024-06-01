package kitchenpos.eatinorders.tobe.repository;

import kitchenpos.eatinorders.tobe.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.entity.OrderTable;

import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(UUID id);

    boolean isAllCompleteByOrderTable(OrderTable orderTable);
}
