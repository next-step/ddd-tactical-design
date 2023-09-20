package kitchenpos.ordertables.application;

import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.domain.OrderTableRepository;

import java.util.*;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<OrderTableId, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(final OrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(final OrderTableId id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<OrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }

    @Override
    public boolean existsByOrderAndStatusNot(OrderTableId orderTableId, EatInOrderStatus status) {
        return false;
    }

}
