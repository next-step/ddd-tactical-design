package kitchenpos.order.eatinorder.application.dto;

import java.util.UUID;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;

public class EatInOrderServiceRs {
    private UUID eatInOrderId;
    private EatInOrderFlow eatInOrderFlow;

    public EatInOrderServiceRs(EatInOrder eatInOrder) {
        this.eatInOrderId = eatInOrder.getId();
        this.eatInOrderFlow = eatInOrder.getEatInOrderFlow();
    }

    public EatInOrderServiceRs(UUID eatInOrderId, EatInOrderFlow eatInOrderFlow) {
        this.eatInOrderId = eatInOrderId;
        this.eatInOrderFlow = eatInOrderFlow;
    }

    public UUID getEatInOrderId() {
        return eatInOrderId;
    }

    public EatInOrderFlow getEatInOrderFlow() {
        return eatInOrderFlow;
    }
}
