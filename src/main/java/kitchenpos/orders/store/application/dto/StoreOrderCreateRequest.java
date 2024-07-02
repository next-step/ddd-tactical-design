package kitchenpos.orders.store.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import kitchenpos.orders.common.application.dto.OrderLineItemsRequest;

public record StoreOrderCreateRequest(@NotNull OrderLineItemsRequest orderLineItemRequests,
                                      @NotNull UUID orderTableId) {

}
