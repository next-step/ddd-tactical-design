package kitchenpos.products.ui.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductChangePriceRestRequest {

    @NotNull
    @Min(0)
    final private BigDecimal price;

    public ProductChangePriceRestRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
