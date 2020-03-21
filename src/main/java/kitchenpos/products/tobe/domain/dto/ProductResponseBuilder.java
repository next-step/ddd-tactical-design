package kitchenpos.products.tobe.domain.dto;

import java.math.BigDecimal;

public final class ProductResponseBuilder {
    private Long id;
    private String name;
    private BigDecimal price;

    private ProductResponseBuilder() {
    }

    public static ProductResponseBuilder aProductResponse() {
        return new ProductResponseBuilder();
    }

    public ProductResponseBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProductResponseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductResponseBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ProductResponse build() {
        return new ProductResponse(id, name, price);
    }
}
