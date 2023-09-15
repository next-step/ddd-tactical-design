package kitchenpos.product.domain;

public final class ProductNameAccessor {

    public static ProductName create(final Name name) {
        return ProductName.create(name);
    }

    private ProductNameAccessor() {
        throw new UnsupportedOperationException();
    }
}
