package kitchenpos.products.tobe.fixture;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;

import java.math.BigDecimal;

public class ProductFixture {

    public static Product createProductRequest(PurgomalumClient purgomalumClient) {
        return createProductRequest("후라이드", 16_000L, purgomalumClient);
    }

    public static Product createProductRequest(final String name, final long price, PurgomalumClient purgomalumClient) {
        return createProductRequest(name, BigDecimal.valueOf(price), purgomalumClient);
    }

    public static Product createProductRequest(final String name, final BigDecimal price, PurgomalumClient purgomalumClient) {
        final ProductName productName = new ProductName(name, purgomalumClient);
        final Price productPrice = new Price(price);
        final Product product = new Product(productName, productPrice);
        return product;
    }
}
