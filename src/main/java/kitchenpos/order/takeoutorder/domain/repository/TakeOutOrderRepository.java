package kitchenpos.order.takeoutorder.domain.repository;

import kitchenpos.order.takeoutorder.domain.model.TakeOutOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TakeOutOrderRepository {

    TakeOutOrder save(TakeOutOrder order);

    Optional<TakeOutOrder> findById(UUID id);

    List<TakeOutOrder> findAll();

}
