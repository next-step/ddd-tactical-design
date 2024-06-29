package kitchenpos.eatinorder.tobe.domain;

import java.util.UUID;

public interface ClearedTable {
    boolean isLastOrder(UUID tableId);
}
