package kitchenpos.eatinorders.application.request;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class EatInOrderLineItemRequest {

    @NotNull
    private UUID id;

    @NotNull
    private Long quantity;

    public EatInOrderLineItemRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
