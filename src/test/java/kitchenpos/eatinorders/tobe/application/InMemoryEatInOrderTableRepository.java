package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryEatInOrderTableRepository implements EatInOrderTableRepository {
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
