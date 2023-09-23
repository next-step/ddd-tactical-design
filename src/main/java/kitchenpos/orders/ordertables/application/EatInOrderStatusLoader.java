package kitchenpos.orders.ordertables.application;

import java.util.UUID;

public interface EatInOrderStatusLoader {
    boolean areAllOrdersComplete(UUID orderTableId);
}
