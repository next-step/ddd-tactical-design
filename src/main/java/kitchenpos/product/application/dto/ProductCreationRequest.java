package kitchenpos.product.application.dto;

import java.math.BigDecimal;

public record ProductCreationRequest(
	String name,
	BigDecimal price
) {
}
