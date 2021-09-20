package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;

public class ProductFixtures {

    public static Product product(final PurgomalumClient profanities) {
        return product("후라이드", BigDecimal.valueOf(16000L), profanities);
    }

    public static Product product(final String name, final BigDecimal price, final PurgomalumClient profanities) {
        final ProductName productName = new ProductName(name, profanities);
        final ProductPrice productprice = new ProductPrice(price);
        final Product product = new Product(productName, productprice);

        return product;
    }
}
