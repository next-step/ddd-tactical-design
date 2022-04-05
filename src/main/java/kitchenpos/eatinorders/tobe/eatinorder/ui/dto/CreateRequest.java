package kitchenpos.eatinorders.tobe.eatinorder.ui.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CreateRequest {
    private final UUID orderTableId;
    private final List<OrderLineItemCreateRequest> orderLineItemRequests;

    public CreateRequest(final UUID orderTableId, final List<OrderLineItemCreateRequest> orderLineItemRequests) {
        this.orderTableId = orderTableId;
        this.orderLineItemRequests = orderLineItemRequests;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemCreateRequest> getOrderLineItemRequests() {
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return Collections.unmodifiableList(orderLineItemRequests);
    }
}
