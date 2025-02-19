package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

import static kitchenpos.product.exception.ProductExceptionMessage.PRODUCT_NAME_CREATION_EXCEPTION;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private final String value;

    protected ProductName(String value) {
        validateName(value);
        this.value = value;
    }

    protected ProductName() {
        this.value = null;
    }

    private void validateName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(PRODUCT_NAME_CREATION_EXCEPTION.getMessage());
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName productName = (ProductName) o;
        return Objects.equals(value, productName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
