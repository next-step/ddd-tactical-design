package kitchenpos.product.tobe.application.dto.request;

import java.math.BigDecimal;

public record CreateProductRequest(String name, BigDecimal price) {
}
