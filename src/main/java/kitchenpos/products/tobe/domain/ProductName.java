package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.exception.ProductNameRequiredException;
import kitchenpos.products.tobe.exception.ProfanityException;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName from(String name, PurgomalumClient purgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new ProductNameRequiredException();
        }

        if (purgomalumClient.containsProfanity(name)) {
            throw new ProfanityException();
        }
        return new ProductName(name);
    }

    public String getName() {
        return name;
    }
}
