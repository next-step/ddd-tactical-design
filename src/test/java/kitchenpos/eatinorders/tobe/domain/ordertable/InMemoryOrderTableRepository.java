package kitchenpos.eatinorders.tobe.domain.ordertable;

import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.*;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<OrderTableId, OrderTable> orderTables;

    public InMemoryOrderTableRepository() {
        this(new HashMap<>());
    }

    public InMemoryOrderTableRepository(final Map<OrderTableId, OrderTable> orderTables) {
        this.orderTables = orderTables;
    }

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

