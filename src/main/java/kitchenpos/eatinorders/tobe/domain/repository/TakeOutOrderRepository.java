package kitchenpos.eatinorders.tobe.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrder;

public interface TakeOutOrderRepository {

    TakeOutOrder save(TakeOutOrder takeOutOrder);

    Optional<TakeOutOrder> findById(UUID id);

    List<TakeOutOrder> findAll();

}
