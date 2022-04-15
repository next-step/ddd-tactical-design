package kitchenpos.products.tobe.domain.fixture;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

import static kitchenpos.products.tobe.domain.fixture.NameFixture.PRODUCT_NAME;
import static kitchenpos.products.tobe.domain.fixture.PriceFixture.PRICE;

public class ProductFixture {
    public static final Product 상품 = new Product(null, PRODUCT_NAME, PRICE);

}
