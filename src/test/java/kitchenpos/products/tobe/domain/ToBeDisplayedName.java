package kitchenpos.products.tobe.domain;

import java.util.Objects;

public class ToBeDisplayedName {
    private final String productName;

    public ToBeDisplayedName(final String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ToBeDisplayedName that = (ToBeDisplayedName) o;
        return Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }
}
