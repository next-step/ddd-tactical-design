package kitchenpos.eatinorders.tobe.domain.orderTable;

import java.util.UUID;

public class ExistOrderTableOrders implements OrderTableOrders {
    @Override
    public boolean existByOrderTableId(final UUID orderTableId) {
        return true;
    }
}
