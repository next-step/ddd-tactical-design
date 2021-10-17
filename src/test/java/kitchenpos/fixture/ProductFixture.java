package kitchenpos.fixture;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.model.ProductPrice;

public class ProductFixture {

    private static final Profanities profanities = new FakeProfanities();

    private static final long PRICE1 = 10000L;
    private static final long PRICE2 = 20000L;
    private static final long CHEAP_PRICE = 1000L;
    private static final String NAME1 = "상품1";
    private static final String NAME2 = "상품2";

    public static Product PRODUCT1() {
        return new Product(new DisplayedName(NAME1, profanities), new ProductPrice(PRICE1));
    }

    public static Product PRODUCT2() {
        return new Product(new DisplayedName(NAME2, profanities), new ProductPrice(PRICE2));
    }

    public static Product CHEAP_PRODUCT() {
        return new Product(new DisplayedName(NAME2, profanities), new ProductPrice(CHEAP_PRICE));
    }

    public static Product PRODUCT_WITH_PRICE(final ProductPrice price) {
        return new Product(new DisplayedName(NAME2, profanities), price);
    }

    public static Product PRODUCT_WITH_NAME(final String name) {
        return new Product(new DisplayedName(name, profanities), new ProductPrice(PRICE1));
    }

}
