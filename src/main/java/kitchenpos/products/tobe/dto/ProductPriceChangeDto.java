package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ProductPriceChangeDto {

    private BigDecimal price;

    public ProductPriceChangeDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
