package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status);
}
