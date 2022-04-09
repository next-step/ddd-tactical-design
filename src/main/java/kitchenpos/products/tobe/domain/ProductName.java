package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    protected ProductName(PurgomalumClient purgomalumClient, String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
