package kitchenpos.order.common.domain.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.eatin.domain.model.OrderTableId;

public record OrderVo() {

    public record OrderInfo(
        OrderId id,
        OrderTableId orderTableId,
        OrderLineItems orderLineItems,
        LocalDateTime orderDateTime,
        OrderStatus status,
        OrderType type
    ) {
        public static OrderInfo fromEntity(Order entity) {
            return new OrderInfo(
                entity.getOrderId(),
                entity.getOrderTableId(),
                OrderLineItems.of(entity.getOrderLineItems().getItems()),
                entity.getOrderDateTime(),
                entity.getStatus(),
                entity.getType());
        }

        public UUID getOrderId() {
            return id.get();
        }

        public UUID getOrderTableId() {
            return Optional.ofNullable(orderTableId)
                .map(OrderTableId::get)
                .orElse(null);
        }

    }

    public record Create(
        OrderType type,
        OrderTableId orderTableId,
        OrderLineItems orderLineItems,
        String deliveryAddress
    ) {

    }

    public record Update(MenuId menuId, MenuPrice price) {}
}
