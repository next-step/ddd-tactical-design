package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;

public class ProductId {
    private final Long id;

    public ProductId(final Long id) {
        this.id = id;
    }

    public Long getValue() {
        return id;
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
