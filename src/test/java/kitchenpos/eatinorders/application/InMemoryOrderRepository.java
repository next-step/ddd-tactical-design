package kitchenpos.eatinorders.application;

import kitchenpos.apply.order.eatinorders.domain.Order;
import kitchenpos.apply.order.eatinorders.domain.OrderRepository;
import kitchenpos.apply.order.eatinorders.domain.OrderStatus;
import kitchenpos.apply.order.eatinorders.domain.OrderTable;

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
