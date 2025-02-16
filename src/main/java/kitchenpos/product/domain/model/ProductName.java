package kitchenpos.product.domain.model;

import java.util.Objects;

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

    public boolean isSameName(String name) {
        if (name == null) {
            return false;
        }
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
