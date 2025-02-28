package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {

    private final Map<EatInOrderId, EatInOrder> eatInOrders;

    public InMemoryEatInOrderRepository() {
        this(new HashMap<>());
    }

    public InMemoryEatInOrderRepository(final Map<EatInOrderId, EatInOrder> eatInOrders) {
        this.eatInOrders = eatInOrders;
    }

    @Override
    public EatInOrder save(final EatInOrder order) {
        eatInOrders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(final EatInOrderId id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }
}
