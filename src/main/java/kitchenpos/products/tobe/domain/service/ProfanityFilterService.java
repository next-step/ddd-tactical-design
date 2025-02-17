package kitchenpos.products.tobe.domain.service;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class ProfanityFilterService {

    private final PurgomalumClient purgomalumClient;

    public ProfanityFilterService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public boolean containsProfanity(final String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
