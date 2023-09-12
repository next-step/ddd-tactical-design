package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name, ProductPurgomalumClient productPurgomalumClient) {
        validateName(name, productPurgomalumClient);
        this.name = name;
    }

    private static void validateName(String name, ProductPurgomalumClient productPurgomalumClient) {
        if (Objects.isNull(name) || productPurgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName)) return false;
        ProductName productName1 = (ProductName) o;
        return Objects.equals(name, productName1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
