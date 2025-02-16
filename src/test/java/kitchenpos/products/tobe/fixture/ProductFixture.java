package kitchenpos.products.tobe.fixture;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductFixture {

    public static Product createProductRequest(PurgomalumClient purgomalumClient) {
        return createProductRequest("후라이드", 16_000L, purgomalumClient);
    }

    public static Product createProductRequest(final String name, final long price, PurgomalumClient purgomalumClient) {
        return createProductRequest(name, BigDecimal.valueOf(price), purgomalumClient);
    }

    public static Product createProductRequest(final String name, final BigDecimal price, PurgomalumClient purgomalumClient) {
        final Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(new ProductName(name, purgomalumClient));
        product.setPrice(new Price(price));
        return product;
    }
}
