package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadEatInOrderPort {
    List<EatInOrder> findAll();
    Optional<EatInOrder> findById(UUID id);
    boolean existsByOrderTableAndStatusNot(UUID orderTableId, EatInOrderStatus status);
}
