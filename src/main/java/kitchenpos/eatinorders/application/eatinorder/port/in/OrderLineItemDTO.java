package kitchenpos.eatinorders.application.eatinorder.port.in;

import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.OrderLineItemNew;

public final class OrderLineItemDTO {

    private final UUID id;
    private final UUID menuId;
    private final int quantity;

    public OrderLineItemDTO(final OrderLineItemNew orderLineItem) {
        this.id = orderLineItem.getId();
        this.menuId = orderLineItem.getMenuId();
        this.quantity = orderLineItem.getQuantity();
    }
}
