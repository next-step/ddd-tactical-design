package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderTableId;
import kitchenpos.eatinorders.tobe.domain.common.OrderEntity;
import kitchenpos.eatinorders.tobe.domain.common.OrderId;
import kitchenpos.eatinorders.tobe.domain.common.OrderStatus;

import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {

    private Map<OrderId, OrderEntity> orders = new HashMap<>();

    @Override
    public OrderEntity save(OrderEntity order) {
        orders.put(order.id(), order);
        return order;
    }

    @Override
    public Optional<OrderEntity> findById(OrderId id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<OrderEntity> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(OrderTableId orderTableId, OrderStatus status) {
        return orders.values()
                .stream()
                .anyMatch(order -> order.orderTableId().equals(orderTableId)
                        && order.status() != status);
    }
}
