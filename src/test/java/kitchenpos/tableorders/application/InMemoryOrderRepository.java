package kitchenpos.tableorders.application;

import kitchenpos.tableorders.domain.Order;
import kitchenpos.tableorders.domain.OrderRepository;
import kitchenpos.tableorders.domain.OrderStatus;
import kitchenpos.tableorders.domain.OrderTable;

import java.util.*;

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
