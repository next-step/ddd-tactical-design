package kitchenpos.ordertables.application;

import java.util.UUID;

public interface EatInOrderStatusLoader {
    boolean areAllOrdersComplete(UUID orderTableId);
}
