package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public record CreateProductRequestDto(String name, BigDecimal price) {

}
