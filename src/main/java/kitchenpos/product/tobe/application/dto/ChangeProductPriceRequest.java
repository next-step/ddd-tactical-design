package kitchenpos.product.tobe.application.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ChangeProductPriceRequest {

    @NotNull
    @PositiveOrZero
    private final BigDecimal price;

    public ChangeProductPriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
