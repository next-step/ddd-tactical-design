package kitchenpos.eatinorders.tobe.application.dto.response;

import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.domain.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderResponse {
    private UUID id;
    private OrderType type;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private UUID orderTableId;
    private List<OrderLineItemResponse> orderLineItems;

    public OrderResponse() {
    }

    private OrderResponse(UUID id, OrderType type, OrderStatus status, LocalDateTime orderDateTime, UUID orderTableId, List<OrderLineItemResponse> orderLineItems) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderTableId = orderTableId;
        this.orderLineItems = orderLineItems;
    }

    public static OrderResponse of(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getType(),
            order.getStatus(),
            order.getOrderDateTime(),
            order.getOrderTableId(),
            order.getOrderLineItems().stream()
                .map(OrderLineItemResponse::of)
                .collect(Collectors.toList())
        );
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemResponse> getOrderLineItems() {
        return orderLineItems;
    }

    public static class OrderLineItemResponse {
        private Long id;
        private UUID menuId;
        private long quantity;
        private BigDecimal price;

        public OrderLineItemResponse() {
        }

        private OrderLineItemResponse(Long id, UUID menuId, long quantity, BigDecimal price) {
            this.id = id;
            this.menuId = menuId;
            this.quantity = quantity;
            this.price = price;
        }

        public static OrderLineItemResponse of(OrderLineItem orderLineItem) {
            return new OrderLineItemResponse(
                orderLineItem.getSeq(),
                orderLineItem.getMenuId(),
                orderLineItem.getQuantity(),
                orderLineItem.getPrice()
            );
        }

        public Long getId() {
            return id;
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
