package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class DisplayName {

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    protected DisplayName() {
    }

    public DisplayName(String name, DisplayNamePolicy displayNamePolicy) {
       displayNamePolicy.validate(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
