package kitchenpos.eatinorders.tobe.domain.order.event;

import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;

import java.util.UUID;

public record EatInOrderCompletedEvent(
        UUID eventId,
        OrderTableId orderTableId
) {
}
