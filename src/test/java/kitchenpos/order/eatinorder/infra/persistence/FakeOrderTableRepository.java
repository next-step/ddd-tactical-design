package kitchenpos.order.eatinorder.infra.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.order.eatinorder.domain.model.OrderTable;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;

public class FakeOrderTableRepository implements OrderTableRepository {

    private final Map<UUID, OrderTable> storage;

    public FakeOrderTableRepository(Map<UUID, OrderTable> storage) {
        this.storage = storage;
    }

    @Override
    public OrderTable save(OrderTable orderTable) {
        UUID id = UUID.randomUUID();
        orderTable.setId(id);
        storage.put(id, orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(UUID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<OrderTable> findAll() {
        return new ArrayList<>(storage.values());
    }
}
