package kitchenpos.apply.order.eatinorder.tobe.domain;


import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTable;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderTableRepository;

import java.util.*;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<UUID, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(final OrderTable orderTable) {
        orderTables.put(UUID.fromString(orderTable.getId()), orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(final UUID id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public Optional<OrderTable> findByIdNotOccupied(UUID id) {
        return orderTables.values().stream()
                .filter(it -> it.getId().equals(id.toString()))
                .filter(it -> !it.isOccupied())
                .findAny();
    }

    @Override
    public Optional<OrderTable> findByIdOccupied(UUID id) {
        return orderTables.values().stream()
                .filter(it -> it.getId().equals(id.toString()))
                .filter(OrderTable::isOccupied)
                .findAny();
    }

    @Override
    public List<OrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
