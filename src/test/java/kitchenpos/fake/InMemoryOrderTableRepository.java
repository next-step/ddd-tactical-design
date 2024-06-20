package kitchenpos.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.orders.store.domain.OrderTable;
import kitchenpos.orders.store.domain.OrderTableRepository;

public class InMemoryOrderTableRepository implements OrderTableRepository {

    private final HashMap<UUID, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(OrderTable entity) {
        orderTables.put(entity.getId(), entity);
        return entity;
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
