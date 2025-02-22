package kitchenpos.application;

import kitchenpos.eatinorder.application.port.out.EatInOrderTableRepository;
import kitchenpos.eatinorder.domain.EatInOrderTable;

import java.util.*;

public class InMemoryOrderTableRepository implements EatInOrderTableRepository {
    private final Map<UUID, EatInOrderTable> orderTables = new HashMap<>();

    @Override
    public EatInOrderTable save(final EatInOrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<EatInOrderTable> findById(final UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<EatInOrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
