package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

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

    public ProductName(String name, PurgomalumClient purgomalumClient) {
        checkName(name);
        checkProfanity(name, purgomalumClient);
        this.name = name;
    }

    private void checkProfanity(String name, PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
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
