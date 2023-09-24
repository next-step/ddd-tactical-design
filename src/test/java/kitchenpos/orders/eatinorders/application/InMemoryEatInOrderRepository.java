package kitchenpos.orders.eatinorders.application;

import kitchenpos.orders.eatinorders.domain.*;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<EatInOrderId, EatInOrder> orders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(final EatInOrderId id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTableId orderTableId, final EatInOrderStatus status) {
        return orders.values()
                .stream()
                .anyMatch(order -> order.getOrderTableId().equals(orderTableId) && order.getStatus() != status);
    }
}
