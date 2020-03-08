package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class ProductBuilder {
    private Long id;
    private String name;
    private Price price;

    private ProductBuilder() {
    }

    public static ProductBuilder product() {
        return new ProductBuilder();
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withPrice(BigDecimal price) {
        this.price = new Price(price);
        return this;
    }

    public ProductBuilder withFriedChicken() {
        this.name = "후라이드";
        this.price = new Price(BigDecimal.valueOf(16_000L));
        return this;
    }

    public ProductBuilder withSeasonedChicken() {
        this.name = "양념치킨";
        this.price = new Price(BigDecimal.valueOf(16_000L));
        return this;
    }

    public Product build() {
        Product product = new Product(this.name, this.price.toBigDecimal());
        return product;
    }
}
