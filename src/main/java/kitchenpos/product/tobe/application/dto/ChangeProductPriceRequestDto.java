package kitchenpos.product.tobe.application.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ChangeProductPriceRequestDto {

    @NotNull
    @PositiveOrZero
    private final BigDecimal price;

    public ChangeProductPriceRequestDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
