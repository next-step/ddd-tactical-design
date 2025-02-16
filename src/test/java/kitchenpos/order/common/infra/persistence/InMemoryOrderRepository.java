package kitchenpos.order.common.infra.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.common.model.Order;
import kitchenpos.order.common.model.OrderStatus;
import kitchenpos.order.common.repository.OrderRepository;
import kitchenpos.order.eatinorder.domain.model.OrderTable;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new HashMap<>();

    @Override
    public Order save(final Order order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final OrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getOrderTable().equals(orderTable) && order.getStatus() != status);
    }
}
