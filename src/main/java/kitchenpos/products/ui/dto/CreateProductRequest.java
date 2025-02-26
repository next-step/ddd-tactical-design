package kitchenpos.products.ui.dto;

import java.math.BigDecimal;

public record CreateProductRequest(String name, BigDecimal price) {

}
