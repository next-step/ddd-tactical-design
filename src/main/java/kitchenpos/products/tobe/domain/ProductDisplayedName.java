package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductDisplayedName {
    @Column(name = "displayed_name", nullable = false)
    private String value;

    protected ProductDisplayedName() {
    }

    private ProductDisplayedName(final String value) {
        this.value = value;
    }

    public static ProductDisplayedName from(final String value, final DisplayedNamePolicy displayedNamePolicy) {
        ProductDisplayedName productDisplayedName = new ProductDisplayedName(value);
        displayedNamePolicy.validateDisplayName(productDisplayedName);
        return productDisplayedName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDisplayedName that = (ProductDisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
