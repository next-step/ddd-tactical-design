package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    public ProductName() { }

    public ProductName(final String name) {
        checkName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void checkName(final String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
    }
}
