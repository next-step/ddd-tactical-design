package kitchenpos.fake.repository;


import kitchenpos.eatinorder.domain.EatInOrder;
import kitchenpos.eatinorder.domain.EatInOrderRepository;
import kitchenpos.eatinorder.domain.OrderStatus;
import kitchenpos.eatinorder.domain.OrderTable;

import java.util.*;

public class InMemoryOrderRepository implements EatInOrderRepository {
    private final Map<UUID, EatInOrder> store = new HashMap<>();

    @Override
    public EatInOrder save(EatInOrder order) {
        if (order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        store.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(UUID orderId) {
        return Optional.ofNullable(store.get(orderId));
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status) {
        return store.values().stream()
                .anyMatch(order ->
                        Objects.equals(order.getOrderTable(), orderTable) &&
                                order.getStatus() != status
                );
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(store.values());
    }
}
