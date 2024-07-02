package kitchenpos.orders.common.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record OrderLineItemRequest(@NotNull UUID menuId,
                                   long quantity) {

}
