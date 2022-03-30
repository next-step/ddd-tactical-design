package kitchenpos.products.tobe.ui.dto;

import kitchenpos.products.tobe.domain.Price;

import java.math.BigDecimal;

public class ProductChangePriceRequest {
    private final BigDecimal price;

    public ProductChangePriceRequest(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Price price() {
        return new Price(price);
    }

}
