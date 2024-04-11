package kitchenpos.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderStatus;

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
    public boolean existsByOrderTableIdAndStatusNot(UUID orderTableId, EatInOrderStatus status) {
        return orders.values()
            .stream()
            .filter(it -> orderTableId.equals(it.getOrderTableId()))
            .anyMatch(it -> it.getStatus() != status);
    }
}
