package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(TobeOrderTable orderTable, EatInOrderStatus eatInOrderStatus);
}
