package kitchenpos.eatinorders.tobe.application.dto;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TobeOrderResponse {
    private final UUID id;
    private final OrderStatus status;
    private final LocalDateTime orderDateTime;
    private final List<TobeOrderLineItemResponse> orderLineItems;
    private final UUID orderTableId;

    public static class TobeOrderLineItemResponse {
        private final UUID menuId;
        private final long quantity;
        private final BigDecimal price;

        public TobeOrderLineItemResponse(final UUID menuId, final long quantity, final BigDecimal price) {
            this.menuId = menuId;
            this.quantity = quantity;
            this.price = price;
        }

        public static TobeOrderResponse.TobeOrderLineItemResponse from(TobeOrderLineItem item) {
            return new TobeOrderResponse.TobeOrderLineItemResponse(item.getMenuId(), item.getQuantity(), item.getPrice());
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

    public TobeOrderResponse(final UUID id, final OrderStatus status, final LocalDateTime orderDateTime,
                             final List<TobeOrderLineItemResponse> orderLineItems,
                             final UUID orderTableId) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public static TobeOrderResponse from(TobeOrder tobeOrder) {
        return new TobeOrderResponse(tobeOrder.getId(), tobeOrder.getStatus(), tobeOrder.getOrderDateTime(),
                                     tobeOrder.getOrderLineItemsStream()
                                              .map(TobeOrderLineItemResponse::from)
                                              .collect(Collectors.toList()), tobeOrder.getOrderTableId());

    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public List<TobeOrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
