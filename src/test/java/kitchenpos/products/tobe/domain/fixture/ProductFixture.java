package kitchenpos.products.tobe.domain.fixture;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

import static kitchenpos.products.tobe.domain.fixture.NameFixture.PRODUCT_NAME;

public class ProductFixture {
  public static final Product 상품 = new Product(null, PRODUCT_NAME, BigDecimal.valueOf(1000L));

}
