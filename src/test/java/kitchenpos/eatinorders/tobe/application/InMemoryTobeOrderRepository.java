package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTobeOrderRepository implements TobeOrderRepository {
    private final Map<UUID, TobeOrder> orders = new HashMap<>();

    @Override
    public TobeOrder save(final TobeOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<TobeOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<TobeOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final TobeOrderTable orderTable, final OrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getOrderTableId() == orderTable.getId() && order.getStatus() != status);
    }
}
