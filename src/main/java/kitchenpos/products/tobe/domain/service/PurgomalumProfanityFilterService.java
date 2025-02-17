package kitchenpos.products.tobe.domain.service;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class PurgomalumProfanityFilterService implements ProfanityFilterService {

    private final PurgomalumClient purgomalumClient;

    public PurgomalumProfanityFilterService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean containsProfanity(final String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
