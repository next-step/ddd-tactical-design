package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    private String name;

    public ProductName() {
    }

    public ProductName(String name, boolean containsProfanity) {
        if (Objects.isNull(name) || containsProfanity) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductName productName1 = (ProductName) o;

        return name.equals(productName1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
