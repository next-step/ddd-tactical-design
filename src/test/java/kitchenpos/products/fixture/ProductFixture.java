package kitchenpos.products.fixture;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

import static kitchenpos.products.fixture.NameFixture.*;

public class ProductFixture {
    public static final Product 교촌치킨 = new Product(null, 간장반양념반, new ProductPrice(20_000));
}
