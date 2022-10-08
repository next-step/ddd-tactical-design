package kitchenpos.eatinorders.order.tobe.domain;

import java.util.UUID;

public class EatInOrder {

    private UUID id;
    private UUID orderTableId;
    private EatInOrderStatus status;

    public UUID id() {
        return id;
    }

    public UUID orderTableId() {
        return orderTableId;
    }

    public EatInOrderStatus status() {
        return status;
    }
}
