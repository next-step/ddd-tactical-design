package kitchenpos.eatinorders.shared.dto;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.shared.dto.EatInOrderLineItemDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class EatInOrderDto {
    private UUID id;
    private OrderType type;
    private OrderStatus status;
    private LocalDateTime orderDateTime;
    private List<EatInOrderLineItemDto> orderLineItems;

    private OrderTableDto orderTable;

    public OrderTableDto getOrderTable() {
        return orderTable;
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

    public List<EatInOrderLineItemDto> getOrderLineItems() {
        return orderLineItems;
    }
}
