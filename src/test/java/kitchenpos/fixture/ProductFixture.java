package kitchenpos.fixture;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.model.Product;

public class ProductFixture {

    private static final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    private static final long PRICE1 = 10000L;
    private static final long PRICE2 = 20000L;
    private static final long CHEAP_PRICE = 1000L;
    private static final String NAME1 = "상품1";
    private static final String NAME2 = "상품2";

    public static Product PRODUCT1() {
        return new Product(new DisplayedName(NAME1, purgomalumClient), new Price(PRICE1));
    }

    public static Product PRODUCT2() {
        return new Product(new DisplayedName(NAME2, purgomalumClient), new Price(PRICE2));
    }

    public static Product CHEAP_PRODUCT() {
        return new Product(new DisplayedName(NAME2, purgomalumClient), new Price(CHEAP_PRICE));
    }

    public static Product PRODUCT_WITH_PRICE(final Price price) {
        return new Product(new DisplayedName(NAME2, purgomalumClient), price);
    }

    public static Product PRODUCT_WITH_NAME(final String name) {
        return new Product(new DisplayedName(name, purgomalumClient), new Price(PRICE1));
    }

}
