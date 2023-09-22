package kitchenpos.products.application;

import kitchenpos.products.domain.Price;

public class CreateProductRequest {
    private String name;
    private Price price;

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
