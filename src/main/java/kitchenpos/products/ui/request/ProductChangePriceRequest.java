package kitchenpos.products.ui.request;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductChangePriceRequest {

    @NotNull
    private BigDecimal price;

    public ProductChangePriceRequest() {
    }

    public ProductChangePriceRequest(Long price) {
        this.price = price == null ? null : BigDecimal.valueOf(price);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
