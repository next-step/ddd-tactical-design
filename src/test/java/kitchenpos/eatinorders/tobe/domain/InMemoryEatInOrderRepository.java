package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
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
    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final EatInOrderStatus status) {
        return eatInOrders.values()
            .stream()
            .anyMatch(order -> order.getOrderTable().equals(orderTable) && order.getStatus() != status);
    }
}
