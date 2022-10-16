package kitchenpos.eatinorders.domain.tobe;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class InMemoryEatInOrderCreatePolicy implements EatInOrderCreatePolicy {

    private Map<OrderTableId, OrderTable> orderTables = new HashMap<>();

    public void save(final OrderTable orderTable) {
        orderTables.put(orderTable.getId(), orderTable);
    }

    @Override
    public boolean isOccupiedOrderTable(OrderTableId orderTableId) {
        if (!orderTables.containsKey(orderTableId)) {
            throw new NoSuchElementException();
        }

        return !orderTables.get(orderTableId).isOccupied();
    }

}
