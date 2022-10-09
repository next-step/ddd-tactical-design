package kitchenpos.eatinorders.order.tobe.domain;

import java.util.UUID;

public class DummyCleanUp implements CleanUp {

    @Override
    public void clear(UUID orderTableId, EatInOrderRepository eatInOrderRepository) {
        return;
    }
}
