package kitchenpos.takeoutorders.tobe.infra;

import kitchenpos.takeoutorders.tobe.domain.TakeOutOrder;
import kitchenpos.takeoutorders.tobe.domain.TakeOutOrderRepository;

import java.util.*;

public class InMemoryTakeOutOrderRepository implements TakeOutOrderRepository {

    private final Map<UUID, TakeOutOrder> orders = new HashMap<>();

    @Override
    public TakeOutOrder save(final TakeOutOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<TakeOutOrder> findById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<TakeOutOrder> findAll() {
        return new ArrayList<>(orders.values());
    }
}
