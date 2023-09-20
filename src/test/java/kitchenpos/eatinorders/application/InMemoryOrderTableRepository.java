package kitchenpos.eatinorders.application;


import kitchenpos.eatinorders.tobe.domain.ToBeOrderTable;
import kitchenpos.eatinorders.tobe.domain.ToBeOrderTableRepository;

import java.util.*;

public class InMemoryOrderTableRepository implements ToBeOrderTableRepository {
    private final Map<UUID, ToBeOrderTable> orderTables = new HashMap<>();

    @Override
    public ToBeOrderTable save(final ToBeOrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<ToBeOrderTable> findById(final UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<ToBeOrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
