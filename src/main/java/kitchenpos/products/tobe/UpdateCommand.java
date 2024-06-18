package kitchenpos.products.tobe;

import java.math.BigDecimal;

public record UpdateCommand(
        BigDecimal price
) {

}
