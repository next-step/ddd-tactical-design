package kitchenpos.products.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name) {
        validateProductName(name);
        this.name = name;
    }

    private void validateProductName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }
}
