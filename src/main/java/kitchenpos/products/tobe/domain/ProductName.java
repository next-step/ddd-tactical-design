package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    public ProductName(final String name, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public ProductName() {

    }

    public ProductName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
