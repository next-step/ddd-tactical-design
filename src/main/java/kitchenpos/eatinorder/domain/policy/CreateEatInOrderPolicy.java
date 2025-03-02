package kitchenpos.eatinorder.domain.policy;

import java.util.UUID;

public interface CreateEatInOrderPolicy {
    void validateOrderTableAvailability(UUID orderTableId);
}
