package kitchenpos.products.tobe.domain;

import java.util.Objects;

import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;
@Embeddable
public class Name {
    private final String name;
    private final PurgomalumClient purgomalumClient;

    public Name(final String name, final PurgomalumClient purgomalumClient) {
        this.purgomalumClient = Objects.requireNonNull(purgomalumClient, "purgomalumClient must not be null");
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (Objects.isNull(name) || name.isEmpty() || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }
    public String getName() {
        return name;
    }
}
