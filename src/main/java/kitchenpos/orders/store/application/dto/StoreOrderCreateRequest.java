package kitchenpos.orders.store.application.dto;

import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.application.dto.OrderLineItemRequests;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;

public class StoreOrderCreateRequest {

    private final OrderLineItemRequests orderLineItemRequests;
    private final UUID orderTableId;

    public StoreOrderCreateRequest(OrderLineItemRequests orderLineItemRequests, UUID orderTableId) {
        this.orderLineItemRequests = orderLineItemRequests;
        this.orderTableId = orderTableId;
    }

    public List<UUID> getMenuIds() {
        return orderLineItemRequests.getMenuIds();
    }

    public void validate(List<Menu> menus) {
        orderLineItemRequests.validate(menus);
        if (orderTableId == null) {
            throw new IllegalArgumentException();
        }
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItem> toOrderLineItems(List<Menu> menus) {
        return orderLineItemRequests.toOrderLineItems(menus, OrderType.EAT_IN);
    }
}
