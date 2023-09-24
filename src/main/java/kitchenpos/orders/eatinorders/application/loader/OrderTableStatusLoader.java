package kitchenpos.orders.eatinorders.application.loader;

import java.util.UUID;

public interface OrderTableStatusLoader {
    boolean isUnOccupied(UUID orderTableId);
}
