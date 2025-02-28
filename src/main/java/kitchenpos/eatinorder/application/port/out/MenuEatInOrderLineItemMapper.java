package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.application.service.model.OrderLineItemRequests;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;

import java.util.List;

public interface MenuEatInOrderLineItemMapper {
    List<EatInOrderLineItem> toEatInOrderLines(OrderLineItemRequests orderLineItemRequests);
}
