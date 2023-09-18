package kitchenpos.product;

import kitchenpos.product.domain.ProductNameAccessor;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;
import kitchenpos.support.vo.Name;

public final class Fixtures {

    public static final String TEST_NAME = "닭강정";
    public static final Name VALID_NAME = Name.create(TEST_NAME);

    public static final int TEST_PRICE_VALUE = 1_000;
    public static final ProductPrice VALID_PRODUCT_PRICE = ProductPrice.create(TEST_PRICE_VALUE);

    public static ProductNew create(final int price) {
        return create(TEST_NAME, price);
    }

    public static ProductNew create(final String name, final int price) {
        return ProductNew.create(
            ProductNameAccessor.create(Name.create(name)),
            ProductPrice.create(price));
    }

    private Fixtures() {
        throw new UnsupportedOperationException();
    }
}
