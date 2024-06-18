package kitchenpos.eatinorders.tobe.dto.event;

import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;

import java.util.UUID;

public record EatInOrderCompletedEvent(
        UUID orderId,
        UUID orderTableId,
        EatInOrderStatus status
) {
}
