package kitchenpos.eatinorders.order.tobe.domain;

import java.util.UUID;

@FunctionalInterface
public interface CleanUp {
    void clear(final UUID orderTableId, final EatInOrderRepository eatInOrderRepository);
}
