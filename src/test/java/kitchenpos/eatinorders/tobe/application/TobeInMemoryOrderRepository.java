package kitchenpos.eatinorders.tobe.application;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;

public class TobeInMemoryOrderRepository implements OrderRepository {
    private final Map<UUID, Order> orders = new LinkedHashMap<>();

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
    public boolean existsByOrderTableIdAndStatusNot(final UUID orderTableId, final OrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getOrderTableId().equals(orderTableId) && order.getStatus() != status);
    }

}
