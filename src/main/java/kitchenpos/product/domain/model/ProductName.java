package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private static final String NAME_CREATION_EXCEPTION = "이름을 채워주세요!";

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
            throw new IllegalArgumentException(NAME_CREATION_EXCEPTION);
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
