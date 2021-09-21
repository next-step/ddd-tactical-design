package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;

public class ProductFixtures {

    public static Product product(final PurgomalumClient profanities) {
        return product("후라이드", BigDecimal.valueOf(16000L), profanities);
    }

    public static Product product(final String name, final BigDecimal price, final PurgomalumClient profanities) {
        final Name productName = new Name(name, profanities);
        final Price productPrice = new Price(price);
        final Product product = new Product(productName, productPrice);

        return product;
    }
}
