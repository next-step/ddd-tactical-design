package kitchenpos.product.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.product.tobe.domain.service.ProductNamePolicy;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {
    }

    public ProductName(String value) {
        this.value = value;
    }

    public static ProductName of(String value, ProductNamePolicy productNamePolicy) {
        ProductName productName = new ProductName(value);
        productNamePolicy.validate(productName);

        return productName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductName)) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
