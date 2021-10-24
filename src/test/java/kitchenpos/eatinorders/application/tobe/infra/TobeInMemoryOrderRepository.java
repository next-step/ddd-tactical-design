package kitchenpos.eatinorders.application.tobe.infra;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderRepository;

import java.util.*;

public class TobeInMemoryOrderRepository implements TobeOrderRepository {
    private final Map<UUID, TobeOrder> orders = new HashMap<>();

    @Override
    public TobeOrder save(final TobeOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<TobeOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<TobeOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public Optional<TobeOrder> findByOrderTableId(UUID orderTableId) {
        return orders.values().stream()
                .filter(order -> order.getOrderTableId().equals(orderTableId))
                .findAny();
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(final UUID orderTableId, final OrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getOrderTableId().equals(orderTableId) && order.getStatus() != status);
    }
}
