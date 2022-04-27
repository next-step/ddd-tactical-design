package kitchenpos.products.fixture;

import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.Profanities;

public class NameFixture {
    private static final Profanities profanities = new FakeProfanities();
    public static final ProductName 간장반양념반 = new ProductName("간장 반 양념 반", profanities);
}
