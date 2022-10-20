package kitchenpos.products.helper;

import kitchenpos.global.domain.client.ProfanityCheckClient;
import kitchenpos.products.tobe.domain.model.entity.Product;
import kitchenpos.products.tobe.domain.model.vo.ProductName;
import kitchenpos.products.tobe.domain.model.vo.ProductPrice;

public class ProductFixtureFactory {

    private static final ProfanityCheckClient profanityCheckClient = text -> false;

    public static Product createProduct(String name, int price) {
        return new Product(createProductName(name), new ProductPrice(price));
    }

    public static ProductName createProductName(String name) {
        return new ProductName(name, profanityCheckClient);
    }
}
