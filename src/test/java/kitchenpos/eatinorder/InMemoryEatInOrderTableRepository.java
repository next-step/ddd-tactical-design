package kitchenpos.eatinorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.eatinorder.domain.EatInOrderTable;
import kitchenpos.eatinorder.domain.EatInOrderTableRepository;

public class InMemoryEatInOrderTableRepository implements EatInOrderTableRepository {
    private final Map<UUID, EatInOrderTable> orderTables = new HashMap<>();

    @Override
    public EatInOrderTable save(final EatInOrderTable eatInOrderTable) {
        orderTables.put(eatInOrderTable.getId(), eatInOrderTable);
        return eatInOrderTable;
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
