package kitchenpos.products.helper;

import kitchenpos.global.domain.client.ProfanityCheckClient;
import kitchenpos.products.tobe.domain.model.entity.Product;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import kitchenpos.products.tobe.domain.model.vo.ProductPrice;

public class ProductFixtureFactory {

    private static final ProfanityCheckClient profanityCheckClient = text -> false;

    public static Product create(String name, int price) {
        return new Product(
            new ProductName(name, profanityCheckClient),
            new ProductPrice(price)
        );
    }
}
