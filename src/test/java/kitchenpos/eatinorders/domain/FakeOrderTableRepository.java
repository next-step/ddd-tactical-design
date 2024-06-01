package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeOrderTableRepository implements OrderTableRepository {
    private final Map<UUID, OrderTable> repository = new HashMap<>();

    @Override
    public OrderTable save(OrderTable orderTable) {
        repository.put(orderTable.getId(), orderTable);
        return repository.get(orderTable.getId());
    }

    @Override
    public Optional<OrderTable> findBy(UUID id) {
        return Optional.ofNullable(repository.get(id));
    }
}
