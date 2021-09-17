package kitchenpos.eatinordertables.domain;

import java.util.UUID;

public interface OrderTranslator {
    boolean isOrderCompleted(UUID OrderTableId);
}
