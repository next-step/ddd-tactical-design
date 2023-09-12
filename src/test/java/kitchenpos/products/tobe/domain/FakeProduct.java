package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class FakeProduct extends Product {

    public static Product createFakeProduct(String name, BigDecimal price) {
        return new Product(name, price, new FakePurgomalumClient());
    }

}