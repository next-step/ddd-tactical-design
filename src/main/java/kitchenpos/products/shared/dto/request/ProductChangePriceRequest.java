package kitchenpos.products.shared.dto.request;

import java.math.BigDecimal;

public class ProductChangePriceRequest {
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
