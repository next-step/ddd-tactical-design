package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {

    private final Map<UUID, EatInOrder> eatInOrders;

    public InMemoryEatInOrderRepository() {
        this(new HashMap<>());
    }

    public InMemoryEatInOrderRepository(final Map<UUID, EatInOrder> eatInOrders) {
        this.eatInOrders = eatInOrders;
    }

    @Override
    public EatInOrder save(final EatInOrder order) {
        eatInOrders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(final UUID id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }
}
