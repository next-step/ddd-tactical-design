package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;

public interface OrderTableManager {
    OrderTable getOrderTable(final UUID orderTableId);
}
