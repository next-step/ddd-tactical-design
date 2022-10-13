package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

@FunctionalInterface
public interface ClearPolicy {
    void clear(UUID orderTableId, EatInOrderRepository eatInOrderRepository);
}
