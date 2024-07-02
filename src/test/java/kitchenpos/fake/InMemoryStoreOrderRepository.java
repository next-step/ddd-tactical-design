package kitchenpos.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.orders.common.domain.OrderStatus;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.StoreOrder;

public class InMemoryStoreOrderRepository implements StoreOrderRepository {

    private final HashMap<UUID, StoreOrder> orders = new HashMap<>();

    @Override
    public StoreOrder save(StoreOrder entity) {
        orders.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<StoreOrder> findById(UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<StoreOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(OrderTable orderTable, OrderStatus status) {
        return orders.values().stream()
                .anyMatch(order -> isEqualOrderTable(order, orderTable)
                        && !isStatus(order, status));
    }

    private boolean isEqualOrderTable(StoreOrder order, OrderTable orderTable) {
        return order.getOrderTable().getId().equals(orderTable.getId());
    }

    private boolean isStatus(StoreOrder order, OrderStatus status) {
        return order.getStatus().equals(status);
    }
}
