package kitchenpos.product.domain;

import kitchenpos.support.vo.Name;

public final class ProductNameAccessor {

    public static ProductName create(final Name name) {
        return ProductName.create(name);
    }

    private ProductNameAccessor() {
        throw new UnsupportedOperationException();
    }
}
