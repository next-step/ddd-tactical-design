package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;

public class ProductId {

    private final Long value;

    public ProductId(final Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductId productId = (ProductId) o;
        return Objects.equals(getValue(), productId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
