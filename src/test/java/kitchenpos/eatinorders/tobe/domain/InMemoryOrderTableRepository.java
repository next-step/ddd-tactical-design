package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.domain.vo.OrderTableId;

import java.util.*;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<UUID, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(final OrderTable orderTable) {
        orderTables.put(orderTable.id(), orderTable);
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
}
