package kitchenpos.domain;

import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTableRepository;

import java.util.*;

public class FakeOrderTableRepository implements OrderTableRepository {

    private Map<UUID, OrderTable> inMemory = new HashMap<>();

    @Override
    public OrderTable save(OrderTable orderTable) {
        inMemory.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(UUID orderTableId) {
        return Optional.ofNullable(inMemory.get(orderTableId));
    }

    @Override
    public List<OrderTable> findAll() {
        return inMemory.values().stream().toList();
    }
}
