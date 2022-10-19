package kitchenpos.eatinorders.ui.response;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderStatus;

public class EatInOrderResponse {

    private final UUID id;
    private final UUID eatInOrderTableId;
    private final EatInOrderStatus status;
    private final LocalDateTime eatInOrderDateTime;
    private final List<EatInOrderLineItemResponse> eatInOrderLineItems;

    public EatInOrderResponse(
        UUID id,
        UUID eatInOrderTableId,
        EatInOrderStatus status,
        LocalDateTime eatInOrderDateTime,
        List<EatInOrderLineItemResponse> eatInOrderLineItems
    ) {
        this.id = id;
        this.eatInOrderTableId = eatInOrderTableId;
        this.status = status;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public static EatInOrderResponse from(EatInOrder eatInOrder) {
        return new EatInOrderResponse(
            eatInOrder.getId(),
            eatInOrder.getEatInOrderTableId(),
            eatInOrder.getStatus(),
            eatInOrder.getEatInOrderDateTime(),
            EatInOrderLineItemResponse.of(eatInOrder.getEatInOrderLineItemValues())
        );
    }

    public static List<EatInOrderResponse> of(List<EatInOrder> eatInOrders) {
        return eatInOrders.stream()
            .map(EatInOrderResponse::from)
            .collect(toList());
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getEatInOrderDateTime() {
        return eatInOrderDateTime;
    }

    public List<EatInOrderLineItemResponse> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    public UUID getEatInOrderTableId() {
        return eatInOrderTableId;
    }
}
