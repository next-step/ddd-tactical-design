package kitchenpos.product.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest() {

    public record Create() {}
    public record UpdatePrice(UUID productId, BigDecimal price) {}

}
