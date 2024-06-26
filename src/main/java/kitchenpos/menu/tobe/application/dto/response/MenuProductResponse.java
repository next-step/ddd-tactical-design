package kitchenpos.menu.tobe.application.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuProductResponse(Long seq, UUID productId, Long quantity, BigDecimal price) {
}
