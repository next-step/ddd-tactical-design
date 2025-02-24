package kitchenpos.order.eatinorder.ui.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.order.eatinorder.service.dto.CreateEatInOrderServiceRq;
import kitchenpos.order.eatinorder.service.dto.CreateEatInOrderServiceRq.OrderLineItemServiceDto;

public class CreateEatInOrderRq {
    private List<OrderLineItemDto> orderLineItemDtos;
    private UUID orderTableId;

    public CreateEatInOrderRq(List<OrderLineItemDto> orderLineItemDtos, UUID orderTableId) {
        this.orderLineItemDtos = orderLineItemDtos;
        this.orderTableId = orderTableId;
    }

    public CreateEatInOrderRq() {
    }

    public List<OrderLineItemDto> getOrderLineItemDtos() {
        return orderLineItemDtos;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public CreateEatInOrderServiceRq createServiceRq() {
        return new CreateEatInOrderServiceRq(
                this.orderLineItemDtos.stream()
                        .map(o -> new OrderLineItemServiceDto(o.menuId, o.quantity, o.price))
                        .toList(),
                orderTableId
        );
    }

    public static class OrderLineItemDto {
        private UUID menuId;
        private long quantity;
        private BigDecimal price;

        public OrderLineItemDto(UUID menuId, long quantity, BigDecimal price) {
            this.menuId = menuId;
            this.quantity = quantity;
            this.price = price;
        }

        public OrderLineItemDto() {
        }

        public UUID getMenuId() {
            return menuId;
        }

        public long getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
