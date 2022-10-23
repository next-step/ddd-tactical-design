package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.infra.PurgomalumValidator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    public ProductName(String name, PurgomalumValidator purgomalumValidator) {
        if (Objects.isNull(name) || purgomalumValidator.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected ProductName() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
