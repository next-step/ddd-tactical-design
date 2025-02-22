package kitchenpos.takeoutorder.application.port.out;

import kitchenpos.takeoutorder.domain.TakeOutOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TakeOutOrderRepository {
    TakeOutOrder save(TakeOutOrder order);

    Optional<TakeOutOrder> findById(UUID id);

    List<TakeOutOrder> findAll();

}

