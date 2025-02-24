package kitchenpos.order.common.infra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.common.domain.Order;
import kitchenpos.order.common.domain.OrderRepository;
import kitchenpos.order.eatinorder.domain.EatInOrder;
import kitchenpos.order.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.eatinorder.domain.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.OrderTable;

public class InMemoryOrderRepository implements OrderRepository, EatInOrderRepository {

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
    public boolean existsByOrderTableAndStatusNot(final OrderTable orderTable, final EatInOrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> {
                    if (order instanceof EatInOrder eatInOrder) {
                        return eatInOrder.getOrderTable().equals(orderTable) && eatInOrder.getStatus() != status;
                    }
                    return false;
                }
            );
    }
}
