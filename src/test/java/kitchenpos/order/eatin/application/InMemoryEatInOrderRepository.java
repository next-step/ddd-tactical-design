package kitchenpos.order.eatin.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.eatin.domain.EatInOrder;
import kitchenpos.order.eatin.domain.EatInOrderRepository;
import kitchenpos.order.eatin.domain.EatInOrderStatus;
import kitchenpos.order.eatin.domain.OrderTable;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {

    private final Map<UUID, EatInOrder> orders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder order) {
        orders.put(order.getId(), order);
        return order;
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
    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable,
        final EatInOrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(
                order -> order.getOrderTable().equals(orderTable) && order.getStatus() != status);
    }
}
