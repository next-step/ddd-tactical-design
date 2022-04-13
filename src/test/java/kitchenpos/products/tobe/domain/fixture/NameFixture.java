package kitchenpos.products.tobe.domain.fixture;

import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.Profanities;

public class NameFixture {
    private static final Profanities profanities = new FakeProfanities();
    public static final ProductName PRODUCT_NAME = new ProductName("name", profanities);
}
