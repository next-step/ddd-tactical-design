package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;

import java.util.*;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<UUID, OrderTable> orderTables = new HashMap<>();


    @Override
    public OrderTable save(OrderTable orderTable) {
        return orderTables.put(orderTable.getId(), orderTable);
    }

    @Override
    public Optional<OrderTable> findById(UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<OrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
