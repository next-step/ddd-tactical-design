package kitchenpos.eatinorders.feedback;

import kitchenpos.eatinorders.feedback.domain.OrderTable;
import kitchenpos.eatinorders.feedback.domain.OrderTableRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderTableRepository implements OrderTableRepository {
    private final Map<Long, OrderTable> orderTables = new HashMap<>();

    @Override
    public OrderTable save(OrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<OrderTable> findById(Long id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<OrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
