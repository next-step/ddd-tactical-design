package kitchenpos.products.tobe;

import java.math.BigDecimal;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

public final class ProductFixtures {

    private ProductFixtures() {
    }

    public static Product product(String name, long price) {
        return new Product(productName(name), productPrice(price));
    }

    public static ProductPrice productPrice(long value) {
        return new ProductPrice(price(value));
    }

    public static ProductName productName(String value) {
        return new ProductName(name(value));
    }

    private static Price price(long value) {
        return new Price(BigDecimal.valueOf(value));
    }

    private static Name name(String value) {
        return new Name(value, new FakePurgomalumClient());
    }
}
