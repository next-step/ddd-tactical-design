package kitchenpos.eatinorders.todo.domain.orders;

import kitchenpos.eatinorders.todo.domain.ordertables.OrderTable;

import java.util.UUID;

public interface OrderTableClient {
    OrderTable getOrderTable(UUID orderTableId);
}
