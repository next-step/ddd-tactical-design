package kitchenpos.takeoutorder.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TakeoutOrderRepository {
    TakeoutOrder save(TakeoutOrder takeoutOrder);

    Optional<TakeoutOrder> findById(UUID id);

    List<TakeoutOrder> findAll();
}
