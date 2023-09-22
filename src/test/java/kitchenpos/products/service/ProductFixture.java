package kitchenpos.products.service;

import java.util.UUID;

import kitchenpos.products.domain.DisplayedName;
import kitchenpos.products.domain.Price;
import kitchenpos.products.domain.Product;
import kitchenpos.products.tobe.domain.FakePurgomalumClient;

public class ProductFixture {
    private UUID id;
    private String name;
    private long price;

    public ProductFixture() {
        id = UUID.randomUUID();
        name = "치킨";
        price = 0L;
    }

    public static ProductFixture builder() {
        return new ProductFixture();
    }

    public ProductFixture name(String name) {
        this.name = name;
        return this;
    }

    public ProductFixture price(long price) {
        this.price = price;
        return this;
    }

    public Product build() {
        return new Product(id, new DisplayedName(name, new FakePurgomalumClient()), new Price(price));
    }

    public static class Data {
        public static Product 강정치킨() {
            return builder()
                    .name("강정치킨")
                    .price(18000L)
                    .build();
        }

        public static Product 양념치킨() {
            return builder()
                    .name("양념치킨")
                    .price(17000L)
                    .build();
        }
    }
}
