package kitchenpos.order.eatinorder.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.eatinorder.ordertable.domain.OrderTable;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(OrderTable orderTable, EatInOrderStatus status);
}
