package kitchenpos.eatinorder.tobe.domain.order;

import java.util.UUID;

public interface ClearedTable {
    boolean isLastOrder(UUID tableId);
}
