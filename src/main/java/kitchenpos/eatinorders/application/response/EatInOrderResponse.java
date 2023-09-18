package kitchenpos.eatinorders.application.response;

import kitchenpos.eatinorders.domain.tobe.EatInOrder;
import kitchenpos.eatinorders.domain.tobe.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EatInOrderResponse {

    private UUID id;
    private OrderStatus status;
    private LocalDateTime orderDatetime;
    private List<EatInOrderLineItemResponse> eatInOrderLineItems;
    private OrderTableResponse orderTable;

    public EatInOrderResponse(UUID id, OrderStatus status, LocalDateTime orderDatetime, List<EatInOrderLineItemResponse> eatInOrderLineItems, OrderTableResponse orderTable) {
        this.id = id;
        this.status = status;
        this.orderDatetime = orderDatetime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.orderTable = orderTable;
    }

    public static EatInOrderResponse of(EatInOrder eatInOrder) {
        return new EatInOrderResponse(
                eatInOrder.getId(),
                eatInOrder.getStatus(),
                eatInOrder.getOrderDateTime(),
                eatInOrder.getEatInOrderLineItems().stream().map(EatInOrderLineItemResponse::of).collect(Collectors.toList()),
                OrderTableResponse.of(eatInOrder.getOrderTable())
        );
    }

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDatetime() {
        return orderDatetime;
    }

    public List<EatInOrderLineItemResponse> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    public OrderTableResponse getOrderTable() {
        return orderTable;
    }
}
