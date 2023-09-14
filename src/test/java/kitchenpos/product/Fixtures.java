package kitchenpos.product;

import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductPrice;

public final class Fixtures {

    public static final String TEST_NAME = "닭강정";
    public static final Name VALID_NAME = new Name(TEST_NAME);
    
    public static final long TEST_PRICE_VALUE = 1_000L;
    public static final ProductPrice VALID_PRODUCT_PRIECE = ProductPrice.of(TEST_PRICE_VALUE);

    private Fixtures() {
        throw new UnsupportedOperationException();
    }
}
