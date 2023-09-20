package kitchenpos.eatinorders.application;


import kitchenpos.eatinorders.tobe.domain.ToBeOrder;
import kitchenpos.eatinorders.tobe.domain.ToBeOrderRepository;
import kitchenpos.eatinorders.tobe.domain.ToBeOrderStatus;
import kitchenpos.eatinorders.tobe.domain.ToBeOrderTable;

import java.util.*;

public class InMemoryOrderRepository implements ToBeOrderRepository {
    private final Map<UUID, ToBeOrder> orders = new HashMap<>();

    @Override
    public ToBeOrder save(final ToBeOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<ToBeOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<ToBeOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final ToBeOrderTable orderTable, final ToBeOrderStatus status) {
        return orders.values()
            .stream()
            .anyMatch(order -> order.getOrderTableId().equals(orderTable.getId()) && order.getStatus() != status);
    }
}
