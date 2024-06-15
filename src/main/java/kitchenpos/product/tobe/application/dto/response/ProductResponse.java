package kitchenpos.product.tobe.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(UUID id, String name, BigDecimal price) {
}
