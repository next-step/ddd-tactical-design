package kitchenpos.orders.ordertables.application.loader;

import java.util.UUID;

public interface EatInOrderStatusLoader {
    boolean areAllOrdersComplete(UUID orderTableId);
}
