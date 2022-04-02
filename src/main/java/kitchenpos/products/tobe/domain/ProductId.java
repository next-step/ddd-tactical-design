package kitchenpos.products.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public final class ProductId {
    private final UUID value;

    public ProductId(UUID value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductId)) {
            return false;
        }
        ProductId productId = (ProductId) o;
        return Objects.equals(value, productId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ProductId{" +
            "value=" + value +
            '}';
    }
}
