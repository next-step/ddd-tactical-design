package kitchenpos.products.ui.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductChangePriceRequest {

    @NotNull
    @Min(0)
    private BigDecimal price;

    public ProductChangePriceRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
