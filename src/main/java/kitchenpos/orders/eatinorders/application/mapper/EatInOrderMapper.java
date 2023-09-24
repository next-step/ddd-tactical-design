package kitchenpos.orders.eatinorders.application.mapper;

import kitchenpos.common.domain.Price;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderLineItemRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderLineItemResponse;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.orders.eatinorders.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class EatInOrderMapper {

    public static EatInOrder toEntity(EatInOrderRequest request, List<EatInOrderLineItem> orderLineItems) {
        return new EatInOrder(
                new EatInOrderLineItems(orderLineItems),
                new OrderTableId(request.getOrderTableId())
        );
    }

    public static EatInOrderLineItem toItemEntity(EatInOrderLineItemRequest request, OrderedMenu orderedMenu) {
        return new EatInOrderLineItem(
                new EatInOrderLineItemQuantity(request.getQuantity()),
                new Price(request.getOrderPrice()),
                orderedMenu
        );
    }

    public static EatInOrderResponse toDto(EatInOrder eatInOrder) {
        return new EatInOrderResponse(
                eatInOrder.getIdValue(),
                eatInOrder.getStatus(),
                eatInOrder.getOrderDateTimeValue(),
                eatInOrder.getOrderTableIdValue(),
                toItemDtos(eatInOrder.getOrderLineItemValues())
        );
    }

    public static List<EatInOrderResponse> toDtos(List<EatInOrder> eatInOrder) {
        return eatInOrder.stream()
                .map(EatInOrderMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public static EatInOrderLineItemResponse toDto(EatInOrderLineItem orderLineItem) {
        return new EatInOrderLineItemResponse(
                orderLineItem.getSeqValue(),
                orderLineItem.getMenuIdValue(),
                orderLineItem.getQuantityValue(),
                orderLineItem.getPriceValue()
        );
    }

    private static List<EatInOrderLineItemResponse> toItemDtos(List<EatInOrderLineItem> orderLineItems) {
        return orderLineItems.stream()
                .map(EatInOrderMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }


}
