package kitchenpos.products.service;

import kitchenpos.products.application.dto.ChangePriceRequest;
import kitchenpos.products.application.dto.CreateProductRequest;
import kitchenpos.products.domain.Price;

public class ProductRequestFixture {
    private String name;
    private Long price;

    public ProductRequestFixture() {
        name = "치킨";
        price = 10000L;
    }

    public static ProductRequestFixture builder() {
        return new ProductRequestFixture();
    }

    public ProductRequestFixture name(String name) {
        this.name = name;
        return this;
    }

    public ProductRequestFixture price(Long price) {
        this.price = price;
        return this;
    }

    public CreateProductRequest buildCreateRequest() {
        return new CreateProductRequest(name, price);
    }

    public ChangePriceRequest changePriceRequest() {
        return new ChangePriceRequest(price);
    }

}
