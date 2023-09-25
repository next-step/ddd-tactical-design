package kitchenpos.eatinorders.application.dto;

import java.util.UUID;

public class EatInOrderLineItemResponse {
    private Long seq;
    private UUID menuId;
    private long quantity;

    private EatInOrderLineItemResponse(Long seq, UUID menuId, long quantity) {
        this.seq = seq;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public static EatInOrderLineItemResponse create(Long seq, UUID menuId, long quantity) {
        return new EatInOrderLineItemResponse(seq, menuId, quantity);
    }
}
