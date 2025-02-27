package kitchenpos.eatinorder.application.port.out;

import kitchenpos.eatinorder.adapter.out.persistance.entity.OrderLineItem;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;

import java.util.List;

public interface MenuEatInOrderLineItemMapper {
    List<EatInOrderLineItem> toEatInOrderLines(List<OrderLineItem> orderLineItemRequests);
}
