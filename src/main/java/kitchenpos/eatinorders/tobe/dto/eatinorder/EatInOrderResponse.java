package kitchenpos.eatinorders.tobe.dto.eatinorder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderStatus;
import kitchenpos.shared.domain.OrderType;

public record EatInOrderResponse(UUID id,
                                 OrderType type,
                                 EatInOrderStatus status,
                                 LocalDateTime orderDateTime,
                                 List<EatInOrderLineItem> eatInOrderLineItems,
                                 UUID restaurantTableId) {

    public static EatInOrderResponse from(EatInOrder eatInOrder) {
        return new EatInOrderResponse(
            eatInOrder.getId(),
            eatInOrder.getType(),
            eatInOrder.getStatus(),
            eatInOrder.getOrderDateTime(),
            eatInOrder.getOrderLineItems(),
            eatInOrder.getRestaurantTableId());
    }
}
