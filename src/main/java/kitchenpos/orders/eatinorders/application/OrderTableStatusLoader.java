package kitchenpos.orders.eatinorders.application;

import java.util.UUID;

public interface OrderTableStatusLoader {
    boolean isUnOccupied(UUID orderTableId);
}
