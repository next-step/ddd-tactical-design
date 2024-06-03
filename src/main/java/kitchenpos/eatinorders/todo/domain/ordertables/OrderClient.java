package kitchenpos.eatinorders.todo.domain.ordertables;

import java.util.UUID;

public interface OrderClient {
    boolean containsInvalidOrderForClearOrderTable(UUID orderTableId);
}
