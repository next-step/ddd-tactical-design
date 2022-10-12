package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, OrderStatus status);
}
