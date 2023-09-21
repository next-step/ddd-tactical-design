package kitchenpos.eatinorders.application.orderTables;

import java.util.UUID;

public interface OrderClient {
    boolean isCompletedEatInOrder(UUID orderTableId);
}
