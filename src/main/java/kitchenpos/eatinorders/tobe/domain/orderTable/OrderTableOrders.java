package kitchenpos.eatinorders.tobe.domain.orderTable;

import java.util.UUID;

public interface OrderTableOrders {
    boolean existByOrderTableId(final UUID orderTableId);
}
