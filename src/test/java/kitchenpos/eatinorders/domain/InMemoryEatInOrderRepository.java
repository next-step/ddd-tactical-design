package kitchenpos.eatinorders.domain;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<UUID, EatInOrder> orders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder eatInOrder) {
        orders.put(eatInOrder.getId(), eatInOrder);
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByEatInOrderTableIdAndStatusNot(
        UUID eatInOrderTableId,
        EatInOrderStatus status
    ) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getEatInOrderTableId().equals(eatInOrderTableId) && order.getStatus() != status);
    }
}
