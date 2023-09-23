package kitchenpos.orders.eatinorders.ui.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EatInOrderRestResponse {

    final private UUID id;
    final private String eatInOrderStatus;
    final private LocalDateTime orderDateTime;
    final private UUID orderTableId;
    final private List<EatInOrderLineItemRestResponse> orderLineItems;

    public EatInOrderRestResponse(UUID eatInOrderId, String eatInOrderStatus, LocalDateTime orderDateTime, UUID orderTableId, List<EatInOrderLineItemRestResponse> orderLineItems) {
        this.id = eatInOrderId;
        this.eatInOrderStatus = eatInOrderStatus;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public UUID getId() {
        return id;
    }

    public String getEatInOrderStatus() {
        return eatInOrderStatus;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<EatInOrderLineItemRestResponse> getOrderLineItems() {
        return orderLineItems;
    }

}
