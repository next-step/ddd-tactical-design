package kitchenpos.order.common.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.common.domain.entity.OrderLineItem;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderVo;

public record OrderResponse() {
    public record GetOrder(
        UUID id,
        UUID orderTableId,
        List<GetOrderItems> orderItems,
        LocalDateTime orderDateTime,
        OrderStatus status,
        OrderType type

    ) {
        public static GetOrder fromVo(OrderVo.OrderInfo vo) {
            return new GetOrder(
                vo.getOrderId(),
                vo.getOrderTableId(),
                vo.orderLineItems().getItems().stream()
                    .map(GetOrderItems::fromVo)
                    .toList(),
                vo.orderDateTime(),
                vo.status(),
                vo.type()
            );
        }
    }

    public record GetOrderItems(
        UUID menuId,
        long quantity
    ) {
        public static OrderResponse.GetOrderItems fromVo(OrderLineItem vo) {
            return new OrderResponse.GetOrderItems(vo.getOrderId().get(), vo.getQuantity().get());
        }
    }
}
