package kitchenpos.eatinorders.application.tobe;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;

import java.util.*;

public class TobeInMemoryOrderTableRepository implements TobeOrderTableRepository {
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
