package kitchenpos.domain;

import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderStatus;

import java.util.*;

public class FakeOrderRepository implements OrderRepository {

    private Map<UUID, Order> inMemory = new HashMap<>();

    @Override
    public Optional<Order> findById(UUID orderId) {
        return Optional.ofNullable(inMemory.get(orderId));
    }

    @Override
    public List<Order> findAll() {
        return inMemory.values().stream().toList();
    }

    @Override
    public Order save(Order order) {
        inMemory.put(order.getId(), order);
        return order;
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus orderStatus) {
        return inMemory.values()
                .stream()
                .anyMatch(order -> order.getOrderTable().equals(orderTable) && !order.getStatus().equals(orderStatus));
    }
}
