package kitchenpos.product.tobe.application.dto;

import java.math.BigDecimal;

public record CreateProductRequest(String name, BigDecimal price) {
}
