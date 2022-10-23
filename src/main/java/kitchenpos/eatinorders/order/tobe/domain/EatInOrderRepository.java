package kitchenpos.eatinorders.order.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {

    EatInOrder save(final EatInOrder eatInOrder);

    Optional<EatInOrder> findById(final UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableAndStatusNot(final UUID orderTableId, final EatInOrderStatus status);
}
