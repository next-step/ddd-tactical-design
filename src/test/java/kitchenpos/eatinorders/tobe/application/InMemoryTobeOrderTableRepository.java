package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTobeOrderTableRepository implements TobeOrderTableRepository {
    private final Map<UUID, TobeOrderTable> orderTables = new HashMap<>();

    @Override
    public TobeOrderTable save(final TobeOrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<TobeOrderTable> findById(final UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<TobeOrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
