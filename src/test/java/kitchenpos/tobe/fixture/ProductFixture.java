package kitchenpos.tobe.fixture;

import kitchenpos.tobe.product.domain.Product;
import kitchenpos.tobe.product.domain.ProductName;
import kitchenpos.tobe.product.domain.ProductPrice;

import java.math.BigDecimal;

public class ProductFixture {

    public static Product createProduct(final String name, final BigDecimal price) {
        return new Product(new ProductName(name), new ProductPrice(price));
    }

}
