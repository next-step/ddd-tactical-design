package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {

    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAllByOrderTableId(UUID id);

}
