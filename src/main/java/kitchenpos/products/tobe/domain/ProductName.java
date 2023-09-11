package kitchenpos.products.tobe.domain;

import kitchenpos.products.common.NamePolicy;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    public ProductName() {
    }

    public ProductName(String name, NamePolicy namePolicy) {
        validate(name, namePolicy);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validate(String name, NamePolicy namePolicy) {
        if (Objects.isNull(name) || namePolicy.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

}
