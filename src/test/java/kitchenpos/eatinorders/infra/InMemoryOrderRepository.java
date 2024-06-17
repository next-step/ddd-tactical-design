package kitchenpos.eatinorders.infra;

import kitchenpos.common.domain.orders.OrderStatus;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.domain.eatinorder.Order;
import kitchenpos.eatinorders.domain.eatinorder.OrderRepository;
import kitchenpos.eatinorders.domain.eatinorder.OrderTable;

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
    public boolean existsByOrderTableAndStatusNot(OrderTable orderTableRequest, OrderStatus orderStatus) {
        return orders.values().stream().filter(order -> order.getOrderTableId().equals(orderTableRequest.getId()))
                .anyMatch(order -> order.hasStatus(orderStatus));
    }

}
