package kitchenpos.apply.order.eatinorder.tobe.domain;


import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrderStatus;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTable;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<UUID, EatInOrder> orders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder order) {
        orders.put(UUID.fromString(order.getId()), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public Optional<EatInOrder> findByIdAndStatus(UUID id, EatInOrderStatus status) {
        return orders.values().stream()
                .filter(it -> it.getId().equals(id.toString()))
                .filter(it -> it.getStatus().equals(status))
                .findAny();
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(OrderTable orderTable, EatInOrderStatus status) {
        return orders.values().stream()
                .anyMatch(it -> it.getOrderTable().equals(orderTable) &&
                        !it.getStatus().equals(status));
    }

}
