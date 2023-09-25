package kitchenpos.eatinorders.application.eatinorder.port.in;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.domain.eatinorder.EatinOrder;

public final class EatinOrderDTO {

    private final UUID id;
    private final List<OrderLineItemDTO> orderLineItems;
    private final UUID orderTableId;

    public EatinOrderDTO(final EatinOrder order) {
        this.id = order.getId();
        this.orderLineItems = order.getOrderLineItems()
            .stream()
            .map(OrderLineItemDTO::new)
            .collect(Collectors.toUnmodifiableList());
        this.orderTableId = order.getOrderTableId();
    }
}
