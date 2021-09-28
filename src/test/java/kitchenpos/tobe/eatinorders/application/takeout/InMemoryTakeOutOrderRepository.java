package kitchenpos.tobe.eatinorders.application.takeout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.TakeOutOrder;
import kitchenpos.eatinorders.tobe.domain.repository.TakeOutOrderRepository;

public class InMemoryTakeOutOrderRepository implements TakeOutOrderRepository {

    Map<UUID, TakeOutOrder> takeOutOrders = new HashMap<>();

    @Override
    public TakeOutOrder save(TakeOutOrder takeOutOrder) {
        takeOutOrders.put(takeOutOrder.getId(), takeOutOrder);
        return takeOutOrder;
    }

    @Override
    public Optional<TakeOutOrder> findById(UUID id) {
        return Optional.ofNullable(takeOutOrders.get(id));
    }

    @Override
    public List<TakeOutOrder> findAll() {
        return new ArrayList<>(takeOutOrders.values());
    }
}
