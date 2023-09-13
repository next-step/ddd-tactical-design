package kitchenpos.products.common;

import kitchenpos.common.DomainService;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@DomainService
public class NamePolicy {
    private final PurgomalumClient purgomalumClient;

    public NamePolicy(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void validateDisplayName(String name) {
        if (Objects.isNull(name) || this.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean containsProfanity(String name) {
        return purgomalumClient.containsProfanity(name);
    }

}