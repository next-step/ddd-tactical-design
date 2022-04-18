package kitchenpos.products.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.products.exception.ProductNameException;

@Embeddable
public class ProductName {

    private final String name;

    public ProductName(String name, PurgomalumClient purgomalumClient) {
        validateName(name, purgomalumClient);
        this.name = name;
    }

    private void validateName(String name, PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name) || Objects.isNull(name)) {
            throw new ProductNameException(name);
        }
    }

    public String getName() {
        return name;
    }
}
