package kitchenpos.eatinorders.tobe.domain.order;

import java.util.*;

public class InMemoryOrderRepository implements EatInOrderRepository {

    private final Map<UUID, EatInOrder> eatInOrders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder eatInOrder) {
        eatInOrders.put(eatInOrder.getId(), eatInOrder);
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(final UUID id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(final UUID orderTableId, final OrderStatus status) {
        return eatInOrders.values()
                .stream()
                .anyMatch(eatInOrder -> eatInOrder.getOrderTableId().equals(orderTableId) && eatInOrder.getStatus() != status);
    }
}
