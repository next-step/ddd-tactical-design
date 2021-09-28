package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrderStatus;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder eatInOrder);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, EatInOrderStatus status);
}
