package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<UUID, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(OrderTable orderTable) {
        UUID id = UUID.randomUUID();
        orderTable.setId(id);
        orderTables.put(id, orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }
}
