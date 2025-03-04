package kitchenpos.order.common.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.order.common.domain.entity.OrderLineItem;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.eatin.domain.model.OrderTableId;

public record OrderRequest() {

    public record Create(
        @NotNull(message = "주문유형은 필수입니다.")
        OrderType type,
        UUID orderTableId,
        @NotEmpty(message = "메뉴 구성 상품을 하나 이상 포함해야 합니다.")
        List<OrderLineItemCreate> orderLineItems,
        String deliveryAddress
    ) {
        public OrderVo.Create toVo() {
            return new OrderVo.Create(
                type,
                OrderTableId.of(orderTableId),
                OrderLineItems.of(orderLineItems.stream()
                    .map(item -> OrderLineItem.fromDto(item, null))
                    .collect(Collectors.toList())),
                deliveryAddress
            );
        }
    }

    public record OrderLineItemCreate(UUID menuId, BigDecimal price, long quantity) {}

}
