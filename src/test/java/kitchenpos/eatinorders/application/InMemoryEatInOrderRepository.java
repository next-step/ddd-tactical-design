package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderId;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<OrderId, EatInOrder> orders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(final OrderId id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public List<EatInOrder> findAllByOrderTableId(OrderTableId id) {
        return orders.values()
                .stream()
                .filter(o -> o.getOrderTableId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(TobeOrderTable orderTable, OrderStatus status) {
        return orders.values()
                .stream()
                .anyMatch(order -> order.getOrderTable().equals(orderTable) && order.getStatus() != status);
    }
}
