package kitchenpos.product.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.product.tobe.domain.validate.ProductNameValidator;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name, ProductNameValidator validator) {
        validator.validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
