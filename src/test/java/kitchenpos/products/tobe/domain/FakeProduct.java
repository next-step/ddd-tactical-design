package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;

public class FakeProduct extends Product {

    public static Product createFake(String name, BigDecimal price) {
        return Product.create(
                ProductName.create(name, new FakePurgomalumClient()),
                ProductPrice.create(price)
        );
    }

}
