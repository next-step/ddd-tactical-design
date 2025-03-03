package kitchenpos.products.tobe.ui;

import java.math.BigDecimal;

public record ProductCreateRequest(String name, BigDecimal price) {
}
