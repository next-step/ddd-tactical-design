package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public record CreateProductServiceRequest(String name, BigDecimal price) {

}
