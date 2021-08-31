package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.TobeProduct;

import java.math.BigDecimal;

public class CreateRequest {
    private final String name;
    private final BigDecimal price;

    public CreateRequest(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public TobeProduct toProduct() {
        return new TobeProduct(name, price);
    }
}
