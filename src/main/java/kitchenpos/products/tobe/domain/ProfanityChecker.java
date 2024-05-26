package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class ProfanityChecker {
    private final PurgomalumClient purgomalumClient;

    public ProfanityChecker(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public boolean containsProfanity(String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
