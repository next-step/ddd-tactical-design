package kitchenpos.menu.tobe.application.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateMenuProductRequest(UUID productId, long quantity, BigDecimal price) {
}
