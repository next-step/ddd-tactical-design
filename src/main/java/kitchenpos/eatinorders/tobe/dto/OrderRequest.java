package kitchenpos.eatinorders.tobe.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.model.OrderType;

public class OrderRequest {
    private OrderType type;
    private UUID orderTableId;
    private List<OrderLineItemRequest> orderLineItems;

    protected OrderRequest() {
    }

    public OrderRequest(final OrderType type, final UUID orderTableId, final OrderLineItemRequest ... orderLineItems) {
        this(type, orderTableId, Arrays.asList(orderLineItems));
    }

    public OrderRequest(final OrderType type, final UUID orderTableId, final List<OrderLineItemRequest> orderLineItems) {
        this.type = type;
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public OrderType getType() {
        return type;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemRequest> getOrderLineItems() {
        return orderLineItems;
    }

    public void validate() {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("한개 이상의 메뉴를  주문해야합니다.");
        }
    }
}
