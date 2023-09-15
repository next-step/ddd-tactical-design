package kitchenpos.apply.order.eatinorders.tobe.ui.dto;

import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.apply.order.eatinorders.tobe.domain.EatInOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class EatInOrderResponse {
    private final String id;

    private final EatInOrderStatus status;

    private final LocalDateTime orderDateTime;

    private final List<Long> orderLineItemSequences;

    private final String orderTableId;

    public EatInOrderResponse(EatInOrder eatInOrder) {
        this.id = eatInOrder.getId();
        this.status = eatInOrder.getStatus();
        this.orderDateTime = eatInOrder.getOrderDateTime();
        this.orderLineItemSequences = eatInOrder.getOrderLineItemSequence();
        this.orderTableId = eatInOrder.getOrderTableId();
    }

    public String getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<Long> getOrderLineItemSequences() {
        return orderLineItemSequences;
    }

    public String getOrderTableId() {
        return orderTableId;
    }
}
