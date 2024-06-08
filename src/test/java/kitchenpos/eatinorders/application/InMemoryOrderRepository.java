package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.domain.eatinorder.Order;
import kitchenpos.eatinorders.domain.eatinorder.OrderRepository;
import kitchenpos.common.domain.OrderStatus;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new HashMap<>();

    @Override
    public Order save(final Order order) {
        orders.put(order.getId(), order);
        return orderRequest;
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
    public boolean existsByOrderTableAndStatusNot(final OrderTableRequest orderTableRequest, final OrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getOrderTable().equals(orderTableRequest) && order.getStatus() != status);
    }
}
