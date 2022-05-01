package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import java.util.*;

public class InMemoryTobeOrderTableRepository implements TobeOrderTableRepository {
    private final Map<OrderTableId, TobeOrderTable> orderTables = new HashMap<>();

    @Override
    public TobeOrderTable save(final TobeOrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
        return orderTable;
    }

    @Override
    public Optional<TobeOrderTable> findById(final OrderTableId id) {
        return Optional.ofNullable(orderTables.get(id));
    }

    @Override
    public List<TobeOrderTable> findAll() {
        return new ArrayList<>(orderTables.values());
    }
}
