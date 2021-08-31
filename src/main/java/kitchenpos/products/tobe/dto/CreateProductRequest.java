package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.TobeProduct;

import java.math.BigDecimal;

public class CreateProductRequest {
    private final String name;
    private final BigDecimal price;

    public CreateProductRequest(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TobeProduct toProduct() {
        return new TobeProduct(name, price);
    }
}
