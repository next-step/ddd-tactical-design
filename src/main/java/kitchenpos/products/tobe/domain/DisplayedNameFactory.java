package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DisplayedNameFactory {
    private final PurgomalumClient purgomalumClient;

    public DisplayedNameFactory(final PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public DisplayedName create(final String name) {
        validate(name);
        return new DisplayedName(name);
    }

    private void validate(final String name) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }
}
