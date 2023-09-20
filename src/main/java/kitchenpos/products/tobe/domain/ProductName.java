package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {
    }

    private ProductName(final String value) {
        this.value = value;
    }

    public static ProductName from(final String value, final ProductNamePolicy productNamePolicy) {
        ProductName productName = new ProductName(value);
        productNamePolicy.validateDisplayName(productName);
        return productName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
