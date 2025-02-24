package kitchenpos.product.domain.model;

import kitchenpos.shared.domain.Profanities;
import kitchenpos.product.domain.exception.ProductNameEmptyException;
import kitchenpos.product.domain.exception.ProductNameValidationException;

import java.util.Objects;

public class ProductName {

    private final String name;

    private ProductName(final String name) {
        this.name = name;
    }

    public static ProductName of(
            final String name,
            final Profanities profanities
            ) {
        if (name == null || name.isBlank()) {
            throw new ProductNameEmptyException();
        }
        if (profanities.contains(name)) {
            throw new ProductNameValidationException("상품 이름에 비속어가 포함되어 있습니다. name: " + name);
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
