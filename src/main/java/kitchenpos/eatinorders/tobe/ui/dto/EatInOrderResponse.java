package kitchenpos.eatinorders.tobe.ui.dto;

import kitchenpos.eatinorders.tobe.domain.EatInOrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class EatInOrderResponse {
    private final String id;

    private final EatInOrderStatus status;

    private final LocalDateTime orderDateTime;

    private final List<Long> orderLineItemSequences;

    private final String orderTableId;

    public EatInOrderResponse(String id, EatInOrderStatus status, LocalDateTime orderDateTime, List<Long> orderLineItemSequences, String orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItemSequences = orderLineItemSequences;
        this.orderTableId = orderTableId;
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
