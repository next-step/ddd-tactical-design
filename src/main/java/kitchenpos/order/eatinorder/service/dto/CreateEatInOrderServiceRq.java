package kitchenpos.order.eatinorder.service.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreateEatInOrderServiceRq {
    private List<OrderLineItemServiceDto> orderLineItemDtos;
    private UUID orderTableId;

    public CreateEatInOrderServiceRq(List<OrderLineItemServiceDto> orderLineItemDtos, UUID orderTableId) {
        this.orderLineItemDtos = orderLineItemDtos;
        this.orderTableId = orderTableId;
    }

    public CreateEatInOrderServiceRq() {
    }

    public List<OrderLineItemServiceDto> getOrderLineItemDtos() {
        return orderLineItemDtos;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public static class OrderLineItemServiceDto {
        private UUID menuId;
        private long quantity;
        private BigDecimal price;

        public OrderLineItemServiceDto(UUID menuId, long quantity, BigDecimal price) {
            this.menuId = menuId;
            this.quantity = quantity;
            this.price = price;
        }

        public OrderLineItemServiceDto() {
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
