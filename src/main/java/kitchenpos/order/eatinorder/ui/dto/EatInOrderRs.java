package kitchenpos.order.eatinorder.ui.dto;

import java.util.UUID;
import kitchenpos.order.eatinorder.application.dto.EatInOrderServiceRs;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFlow;

public class EatInOrderRs {
    private UUID eatInOrderId;
    private EatInOrderFlow eatInOrderFlow;

    public EatInOrderRs(EatInOrderServiceRs eatInOrder) {
        this.eatInOrderId = eatInOrder.getEatInOrderId();
        this.eatInOrderFlow = eatInOrder.getEatInOrderFlow();
    }

    public EatInOrderRs() {
    }

    public UUID getEatInOrderId() {
        return eatInOrderId;
    }

    public EatInOrderFlow getEatInOrderFlow() {
        return eatInOrderFlow;
    }
}
