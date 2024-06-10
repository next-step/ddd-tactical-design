package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<UUID, EatInOrder> eatInOrders = new HashMap<>();

    @Override
    public EatInOrder save(EatInOrder eatInOrder) {
        return eatInOrders.put(eatInOrder.getId(), eatInOrder);
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, OrderStatus status) {
        return eatInOrders.values()
                .stream()
                .anyMatch(eatInOrder -> eatInOrder.getOrderTableId().equals(orderTableId) && eatInOrder.getStatus() != status);
    }
}
