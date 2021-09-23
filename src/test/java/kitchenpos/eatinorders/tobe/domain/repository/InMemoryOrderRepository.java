package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class InMemoryOrderRepository implements OrderRepository {

    private final Map<UUID, Order> orderMap = new HashMap<>();

    @Override
    public Order save(final Order order) {
        orderMap.put(order.getId(), order);
        return order;
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final OrderStatus orderStatus) {
        return orderMap.values()
                .stream()
                .filter(order -> order.getOrderTableId().equals(orderTable.getId()))
                .anyMatch(Predicate.not(order -> order.getStatus().equals(orderStatus.getStatus())));
    }
}
