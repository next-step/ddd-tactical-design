package kitchenpos.products.tobe.ui.dto;

import java.math.BigDecimal;

public record CreateProductRequest(String name, BigDecimal price) {

}
