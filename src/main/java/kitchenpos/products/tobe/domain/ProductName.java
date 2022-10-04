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

    protected ProductName(final String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
