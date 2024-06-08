package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<UUID, OrderTableRequest> orderTables = new HashMap<>();

    @Override
    public OrderTableRequest save(final OrderTableRequest orderTableRequest) {
        orderTables.put(orderTableRequest.getId(), orderTableRequest);
        return orderTableRequest;
    }

    @Override
    public Optional<OrderTableRequest> findById(final UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<OrderTableRequest> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
