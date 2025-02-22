package kitchenpos.application;

import kitchenpos.deliveryorder.application.port.out.DeliveryOrderRepository;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;

import java.util.*;

public class InMemoryOrderRepository implements DeliveryOrderRepository {
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
    public boolean existsByOrderTableAndStatusNot(final EatInOrderTable eatInOrderTable, final OrderStatus status) {
        return orders.values()
                .stream()
                .anyMatch(order -> order.getOrderTable().equals(eatInOrderTable) && order.getStatus() != status);
    }
}
