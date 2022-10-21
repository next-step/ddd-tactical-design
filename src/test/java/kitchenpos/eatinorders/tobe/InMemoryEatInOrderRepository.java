package kitchenpos.eatinorders.tobe;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<Long, EatInOrder> eatInOrders = new HashMap<>();

    @Override
    public EatInOrder save(EatInOrder eatInOrder) {
        eatInOrders.put(eatInOrder.getId(), eatInOrder);
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(Long id) {
        return Optional.ofNullable(eatInOrders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(eatInOrders.values());
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(Long orderTableId, OrderStatus status) {
        return eatInOrders.values()
                .stream()
                .anyMatch(order -> isNotCompleted(orderTableId, status, order));
    }

    private boolean isNotCompleted(Long orderTableId, OrderStatus status, EatInOrder order) {
        return order.getOrderTableId().equals(orderTableId) && order.getOrderStatus() != status;
    }
}
