package kitchenpos.products.tobe.dto;

import java.math.BigDecimal;

public class ProductPriceChangeDto {

    private BigDecimal price;

    private ProductPriceChangeDto() {
    }

    public ProductPriceChangeDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
