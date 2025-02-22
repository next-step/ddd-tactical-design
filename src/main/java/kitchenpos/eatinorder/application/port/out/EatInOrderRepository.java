package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorder.domain.EatInOrderStatus;
import kitchenpos.eatinorder.domain.EatInOrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByEatInOrderTableAndStatusNot(EatInOrderTable eatInOrderTable, EatInOrderStatus status);
}

