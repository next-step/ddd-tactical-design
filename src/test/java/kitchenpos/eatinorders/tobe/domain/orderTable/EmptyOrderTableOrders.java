package kitchenpos.eatinorders.tobe.domain.orderTable;

import java.util.UUID;

public class EmptyOrderTableOrders implements OrderTableOrders {
    @Override
    public boolean existByOrderTableId(final UUID orderTableId) {
        return false;
    }
}
