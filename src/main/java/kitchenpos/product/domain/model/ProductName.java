package kitchenpos.product.domain.model;

public class ProductName {
    private final static ProductNameValidator DEFAULT_PRODUCT_NAME_VALIDATOR = new DefaultProductNameValidator();
    private final String name;

    private ProductName(final String name) {
        this.name = name;
    }

    public static ProductName of(final String name, final ProductNameValidator ... additionalProductNameValidators) {
        DEFAULT_PRODUCT_NAME_VALIDATOR.validate(name);
        for (ProductNameValidator productNameValidator : additionalProductNameValidators) {
            productNameValidator.validate(name);
        }
        return new ProductName(name);
    }

    public String value() {
        return name;
    }
}
