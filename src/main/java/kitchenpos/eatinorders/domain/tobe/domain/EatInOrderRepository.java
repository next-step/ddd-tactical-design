package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EatInOrderRepository {
    EatInOrder save(EatInOrder order);

    Optional<EatInOrder> findById(UUID id);

    List<EatInOrder> findAll();

    Optional<EatInOrder> findByOrderMasterId(UUID masterId);

}
