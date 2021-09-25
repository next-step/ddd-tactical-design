package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.UUID;

public interface OrderTableManager {
    OrderTable getOrderTable(final UUID orderTableId);

    void clearOrderTable(final UUID orderTableId);
}
