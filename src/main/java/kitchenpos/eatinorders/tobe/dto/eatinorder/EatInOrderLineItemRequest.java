package kitchenpos.eatinorders.tobe.dto.eatinorder;

import java.math.BigDecimal;
import java.util.UUID;

public record EatInOrderLineItemRequest(UUID menuId, BigDecimal price, long quantity) {

}
