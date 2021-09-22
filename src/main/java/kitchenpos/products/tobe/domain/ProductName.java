package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    protected ProductName() {
    }

    public ProductName(String name, Profanities profanities) {
        if (name == null || profanities.contains(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName)) return false;
        ProductName that = (ProductName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
