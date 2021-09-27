package kitchenpos.products.application.dto;

import java.math.BigDecimal;

public class ProductPriceChangeDto {
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
